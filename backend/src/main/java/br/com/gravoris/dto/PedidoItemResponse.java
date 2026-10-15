package br.com.gravoris.dto;

import java.math.BigDecimal;

/**
 * Resposta contendo informações detalhadas de um item de pedido.
 */
public record PedidoItemResponse(
        Long produtoId,
        String nome,
        String marca,
        String modelo,
        String imagemUrl,
        int quantidade,
        BigDecimal precoUnitario
) {
}
