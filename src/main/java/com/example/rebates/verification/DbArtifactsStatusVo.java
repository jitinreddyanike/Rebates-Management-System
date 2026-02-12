package com.example.rebates.verification;

public record DbArtifactsStatusVo(
        boolean auditTableExists,
        boolean rebateProcedureExists,
        String databaseProduct,
        String message
) {
}
