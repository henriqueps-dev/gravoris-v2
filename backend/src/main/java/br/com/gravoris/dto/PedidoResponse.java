package br.com.gravoris.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Resposta simples de pedido para o frontend.
 */
public record PedidoResponse(
        Long id,
        BigDecimal total,
        String status,
        String dataPedido,
        List<PedidoItemResponse> itens
) {
}
