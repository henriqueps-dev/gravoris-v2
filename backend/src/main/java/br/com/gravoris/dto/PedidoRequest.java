package br.com.gravoris.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Conteúdo enviado pelo frontend para criar um pedido.
 */
public record PedidoRequest(
        @NotNull Long clienteId,
        @NotNull @Size(min = 1) @Valid List<PedidoItemRequest> items
) {
}
