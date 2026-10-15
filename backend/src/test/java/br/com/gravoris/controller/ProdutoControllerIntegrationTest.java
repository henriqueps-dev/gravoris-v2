package br.com.gravoris.controller;

import br.com.gravoris.model.Produto;
import br.com.gravoris.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ProdutoControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProdutoRepository produtoRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Deve carregar a vitrine de produtos ativos")
    void testCarregarVitrine() throws Exception {
        mockMvc.perform(get("/api/produtos/vitrine")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").isNotEmpty())
                .andExpect(jsonPath("$[0].brand").isNotEmpty())
                .andExpect(jsonPath("$[0].price").isNumber())
                .andExpect(jsonPath("$[0].engine").isNotEmpty())
                .andExpect(jsonPath("$[0].topSpeed").isNumber());
    }

    @Test
    @DisplayName("Deve buscar produtos pelo termo (nome ou marca) e retornar produtos ativos quando termo vazio")
    void testBuscarProdutos() throws Exception {
        // Busca com termo 'Ferrari'
        mockMvc.perform(get("/api/produtos/busca")
                        .param("q", "Ferrari")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[*].brand", everyItem(containsStringIgnoringCase("Ferrari"))));

        // Busca com termo vazio (deve retornar todos os produtos ativos)
        mockMvc.perform(get("/api/produtos/busca")
                        .param("q", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));

        // Busca sem o parâmetro q
        mockMvc.perform(get("/api/produtos/busca")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    @DisplayName("Deve buscar produto por ID real e retornar 404 para ID inexistente")
    void testBuscarPorId() throws Exception {
        List<Produto> produtos = produtoRepository.findByAtivoTrue();
        assertFalse(produtos.isEmpty(), "Deve existir ao menos um produto no banco");
        Produto p = produtos.get(0);

        mockMvc.perform(get("/api/produtos/{id}", p.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(p.getId()))
                .andExpect(jsonPath("$.name").value(p.getNome()))
                .andExpect(jsonPath("$.brand").value(p.getMarca()))
                .andExpect(jsonPath("$.model").value(p.getModelo()));

        // ID inexistente deve retornar 404
        mockMvc.perform(get("/api/produtos/{id}", 99999999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Produto não encontrado"));
    }
}
