package com.example.rebates.config;

import org.h2.tools.SimpleResultSet;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class H2StoredProcedureFunctions {

    private static long sequence = 1000L;

    private H2StoredProcedureFunctions() {
    }

    public static ResultSet spSubmitRebateApplication(Connection connection,
                                                       String vendorId,
                                                       String vendorName,
                                                       String productCode,
                                                       Integer quantity,
                                                       BigDecimal unitPrice,
                                                       BigDecimal rebatePercentage,
                                                       String remarks) throws SQLException {

        persistAuditRecord(connection, vendorId, vendorName, productCode, quantity, unitPrice, rebatePercentage, remarks);

        SimpleResultSet resultSet = new SimpleResultSet();
        resultSet.addColumn("o_application_id", java.sql.Types.BIGINT, 20, 0);
        resultSet.addColumn("o_rebate_amount", java.sql.Types.DECIMAL, 18, 2);
        resultSet.addColumn("o_status_code", java.sql.Types.VARCHAR, 20, 0);
        resultSet.addColumn("o_status_message", java.sql.Types.VARCHAR, 255, 0);

        BigDecimal quantityDecimal = BigDecimal.valueOf(quantity == null ? 0 : quantity);
        BigDecimal amount = unitPrice.multiply(quantityDecimal);
        BigDecimal rebateAmount = amount.multiply(rebatePercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        resultSet.addRow(++sequence, rebateAmount, "SUCCESS", "Rebate application submitted successfully");
        return resultSet;
    }

    private static void persistAuditRecord(Connection connection,
                                           String vendorId,
                                           String vendorName,
                                           String productCode,
                                           Integer quantity,
                                           BigDecimal unitPrice,
                                           BigDecimal rebatePercentage,
                                           String remarks) throws SQLException {

        String sql = """
                INSERT INTO rebate_application_audit (
                    vendor_id, vendor_name, product_code, quantity,
                    unit_price, rebate_percentage, remarks
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, vendorId);
            statement.setString(2, vendorName);
            statement.setString(3, productCode);
            statement.setInt(4, quantity);
            statement.setBigDecimal(5, unitPrice);
            statement.setBigDecimal(6, rebatePercentage);
            statement.setString(7, remarks);
            statement.executeUpdate();
        }
    }
}
