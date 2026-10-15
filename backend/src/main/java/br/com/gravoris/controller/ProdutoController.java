package br.com.gravoris.controller;

import br.com.gravoris.dto.ProdutoResponse;
import br.com.gravoris.model.Produto;
import br.com.gravoris.service.ProdutoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST que expõe os endpoints de catálogo de produtos.
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/vitrine")
    public ResponseEntity<List<ProdutoResponse>> carregarVitrine() {
        return ResponseEntity.ok(produtoService.carregarVitrine().stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/busca")
    public ResponseEntity<List<ProdutoResponse>> buscar(@RequestParam(name = "q", required = false) String termo) {
        return ResponseEntity.ok(produtoService.buscar(termo).stream()
                .map(this::toResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(produtoService.buscarPorId(id)));
    }

    private ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getMarca(),
                produto.getModelo(),
                produto.getPreco(),
                produto.getImagemUrl(),
                produto.getDescricao(),
                produto.getAno(),
                produto.getQuilometragem(),
                produto.getCategoria() != null ? produto.getCategoria().name() : null,
                produto.getEstoque(),
                produto.getMotor(),
                produto.getPotencia(),
                produto.getTorque(),
                produto.getVelocidadeMax(),
                produto.getAceleracao()
        );
    }
}
