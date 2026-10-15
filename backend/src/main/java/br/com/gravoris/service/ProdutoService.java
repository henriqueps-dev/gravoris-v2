package br.com.gravoris.service;

import br.com.gravoris.exception.ResourceNotFoundException;
import br.com.gravoris.model.Produto;
import br.com.gravoris.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço que encapsula as regras de busca de produtos.
 */
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> carregarVitrine() {
        return produtoRepository.findByAtivoTrue();
    }

    public List<Produto> buscar(String termo) {
        if (termo == null || termo.isBlank()) {
            return produtoRepository.findByAtivoTrue();
        }
        return produtoRepository.searchByNomeOrMarca(termo);
    }

    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .filter(Produto::isAtivo)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
    }
}