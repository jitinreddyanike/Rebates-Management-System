package com.example.rebates.exception;

import java.time.OffsetDateTime;
import java.util.List;

public record ErrorResponse(
        String errorCode,
        String errorMessage,
        List<String> details,
        OffsetDateTime timestamp
) {
}
