package br.com.gravoris.controller;

import br.com.gravoris.dto.ClienteRequest;
import br.com.gravoris.dto.PedidoItemRequest;
import br.com.gravoris.dto.PedidoRequest;
import br.com.gravoris.model.Cliente;
import br.com.gravoris.model.Pedido;
import br.com.gravoris.model.Produto;
import br.com.gravoris.repository.ClienteRepository;
import br.com.gravoris.repository.PedidoRepository;
import br.com.gravoris.repository.ProdutoRepository;
import br.com.gravoris.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class PedidoControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    private MockMvc mockMvc;
    private final List<Long> createdPedidoIds = new ArrayList<>();
    private final List<Long> createdClienteIds = new ArrayList<>();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        for (Long id : createdPedidoIds) {
            try {
                pedidoRepository.deleteById(id);
            } catch (Exception ignored) {
            }
        }
        for (Long id : createdClienteIds) {
            try {
                clienteRepository.deleteById(id);
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    @DisplayName("Deve criar pedido com sucesso e listar pedidos por cliente")
    void testCriarEListarPedidos() throws Exception {
        long timestamp = System.currentTimeMillis();
        Cliente cliente = clienteService.salvar(new ClienteRequest(
                "Comprador VIP",
                "vip" + timestamp + "@gravoris.com",
                "VipPassword123!",
                "11988887777",
                String.format("%011d", (timestamp % 100000000000L))
        ));
        createdClienteIds.add(cliente.getId());

        List<Produto> produtos = produtoRepository.findByAtivoTrue();
        assertFalse(produtos.isEmpty(), "Produtos devem existir no banco");
        Produto produto = produtos.get(0);
        int estoqueInicial = produto.getEstoque();

        PedidoItemRequest itemReq = new PedidoItemRequest(
                produto.getId(),
                1,
                produto.getPreco()
        );

        PedidoRequest pedidoReq = new PedidoRequest(
                cliente.getId(),
                List.of(itemReq)
        );

        String responseJson = mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value("PENDENTE"))
                .andExpect(jsonPath("$.itens", hasSize(1)))
                .andExpect(jsonPath("$.itens[0].produtoId").value(produto.getId()))
                .andReturn().getResponse().getContentAsString();

        Long pedidoId = objectMapper.readTree(responseJson).get("id").asLong();
        createdPedidoIds.add(pedidoId);

        // Verificar persistência no banco
        assertTrue(pedidoRepository.findById(pedidoId).isPresent());
        Pedido pedidoPersistido = pedidoRepository.findById(pedidoId).get();
        assertEquals(cliente.getId(), pedidoPersistido.getCliente().getId());

        // Verificar baixa no estoque
        Produto produtoAtualizado = produtoRepository.findById(produto.getId()).get();
        assertEquals(estoqueInicial - 1, produtoAtualizado.getEstoque());

        // Restaurar estoque para não afetar outros testes
        produtoAtualizado.setEstoque(estoqueInicial);
        produtoRepository.save(produtoAtualizado);

        // Listar pedidos pelo cliente ID
        mockMvc.perform(get("/api/pedidos/cliente/{clienteId}", cliente.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(pedidoId))
                .andExpect(jsonPath("$[0].itens[0].produtoId").value(produto.getId()));
    }
}
