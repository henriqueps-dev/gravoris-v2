package br.com.gravoris.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * Entidade que representa o produto/veículo disponível para venda.
 */
@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "imagem_url", length = 1000)
    private String imagemUrl;

    private int ano;

    private int quilometragem;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private int estoque;

    private String motor;

    private int potencia;

    private String torque;

    private int velocidadeMax;

    private String aceleracao;

    @Column(nullable = false)
    private boolean ativo = true;
}
