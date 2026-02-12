package com.example.rebates.dto;

import java.math.BigDecimal;

public record RebateApplicationDbResponseDto(
        Long applicationId,
        BigDecimal rebateAmount,
        String statusCode,
        String statusMessage
) {
}
