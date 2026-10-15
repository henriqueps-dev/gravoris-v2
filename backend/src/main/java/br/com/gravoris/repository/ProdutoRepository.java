package br.com.gravoris.repository;

import br.com.gravoris.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByAtivoTrue();

    List<Produto> findByAtivoTrueOrderByIdDesc();

    @Query("SELECT p FROM Produto p WHERE p.ativo = true AND (LOWER(p.nome) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(p.marca) LIKE LOWER(CONCAT('%', :term, '%')))")
    List<Produto> searchByNomeOrMarca(@Param("term") String term);

    boolean existsByMarca(String marca);
}