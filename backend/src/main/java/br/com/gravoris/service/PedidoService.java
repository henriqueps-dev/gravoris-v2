package br.com.gravoris.service;

import br.com.gravoris.dto.PedidoItemRequest;
import br.com.gravoris.dto.PedidoItemResponse;
import br.com.gravoris.dto.PedidoRequest;
import br.com.gravoris.dto.PedidoResponse;
import br.com.gravoris.exception.BadRequestException;
import br.com.gravoris.exception.ResourceNotFoundException;
import br.com.gravoris.model.*;
import br.com.gravoris.repository.PedidoRepository;
import br.com.gravoris.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Serviço que processa pedidos e aplica regras de estoque.
 */
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteService clienteService;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.clienteService = clienteService;
    }

    @Transactional
    public Pedido salvar(PedidoRequest request) {
        Cliente cliente = clienteService.buscarPorId(request.clienteId());

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .dataPedido(LocalDateTime.now())
                .status(StatusPedido.PENDENTE)
                .total(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (PedidoItemRequest itemRequest : request.items()) {
            Produto produto = produtoRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + itemRequest.productId()));

            if (!produto.isAtivo()) {
                throw new BadRequestException("Produto indisponível: " + produto.getNome());
            }

            if (itemRequest.quantity() <= 0) {
                throw new BadRequestException("Quantidade inválida para o produto: " + produto.getNome());
            }

            if (produto.getEstoque() < itemRequest.quantity()) {
                throw new BadRequestException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            produto.setEstoque(produto.getEstoque() - itemRequest.quantity());
            produtoRepository.save(produto);

            BigDecimal precoUnitario = produto.getPreco();
            BigDecimal subtotal = precoUnitario.multiply(BigDecimal.valueOf(itemRequest.quantity()));
            total = total.add(subtotal);

            ItemPedido itemPedido = ItemPedido.builder()
                    .pedido(pedido)
                    .produto(produto)
                    .quantidade(itemRequest.quantity())
                    .precoUnitario(precoUnitario)
                    .build();

            pedido.getItens().add(itemPedido);
        }

        pedido.setTotal(total);
        return pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> buscarPorClienteId(Long clienteId) {
        clienteService.buscarPorId(clienteId);

        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(this::toResponse)
                .toList();
    }

    public PedidoResponse toResponse(Pedido pedido) {
        List<PedidoItemResponse> itens = pedido.getItens().stream()
                .map(item -> new PedidoItemResponse(
                        item.getProduto().getId(),
                        item.getProduto().getNome(),
                        item.getProduto().getMarca(),
                        item.getProduto().getModelo(),
                        item.getProduto().getImagemUrl(),
                        item.getQuantidade(),
                        item.getPrecoUnitario()
                ))
                .toList();

        return new PedidoResponse(
                pedido.getId(),
                pedido.getTotal(),
                pedido.getStatus().name(),
                pedido.getDataPedido().toString(),
                itens
        );
    }
}
