package br.com.gravoris.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Entidade JPA que representa o cliente da loja.
 * A camada de modelo encapsula os dados persistidos no banco.
 */
@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    private String telefone;

    @Column(nullable = false, unique = true)
    private String cpf;

    private LocalDate dataNascimento;

    @Builder.Default
    @Column(nullable = false)
    private boolean ativo = true;
}