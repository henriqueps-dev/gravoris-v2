package br.com.gravoris.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Representa um item de pedido enviado pelo frontend.
 */
public record PedidoItemRequest(
        @NotNull Long productId,
        @Min(1) int quantity,
        @NotNull BigDecimal price
) {
}
