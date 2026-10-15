package br.com.gravoris.dto;

import java.math.BigDecimal;

/**
 * DTO para representar produtos no formato que o Angular espera.
 */
public record ProdutoResponse(
        Long id,
        String name,
        String brand,
        String model,
        BigDecimal price,
        String imageUrl,
        String description,
        int year,
        int mileage,
        String category,
        int stock,
        String engine,
        int power,
        String torque,
        int topSpeed,
        String acceleration
) {
}
