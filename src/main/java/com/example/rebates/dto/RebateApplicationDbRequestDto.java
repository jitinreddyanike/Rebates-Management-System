package com.example.rebates.dto;

import java.math.BigDecimal;

public record RebateApplicationDbRequestDto(
        String vendorId,
        String vendorName,
        String productCode,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal rebatePercentage,
        String remarks
) {
}
