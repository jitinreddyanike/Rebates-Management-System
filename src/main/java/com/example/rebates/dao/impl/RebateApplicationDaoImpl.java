package com.example.rebates.dao.impl;

import com.example.rebates.dao.RebateApplicationDao;
import com.example.rebates.dto.RebateApplicationDbRequestDto;
import com.example.rebates.dto.RebateApplicationDbResponseDto;
import com.example.rebates.exception.DatabaseException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RebateApplicationDaoImpl implements RebateApplicationDao {

    private static final String PROCEDURE_SQL = """
            SELECT o_application_id, o_rebate_amount, o_status_code, o_status_message
            FROM sp_submit_rebate_application(?, ?, ?, ?, ?, ?, ?)
            """;

    private final JdbcTemplate jdbcTemplate;

    public RebateApplicationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public RebateApplicationDbResponseDto saveRebateApplication(RebateApplicationDbRequestDto requestDto) {
        try {
            return jdbcTemplate.queryForObject(
                    PROCEDURE_SQL,
                    (rs, rowNum) -> new RebateApplicationDbResponseDto(
                            rs.getLong("o_application_id"),
                            rs.getBigDecimal("o_rebate_amount"),
                            rs.getString("o_status_code"),
                            rs.getString("o_status_message")
                    ),
                    requestDto.vendorId(),
                    requestDto.vendorName(),
                    requestDto.productCode(),
                    requestDto.quantity(),
                    requestDto.unitPrice(),
                    requestDto.rebatePercentage(),
                    requestDto.remarks()
            );
        } catch (Exception exception) {
            throw new DatabaseException("Failed to execute stored procedure sp_submit_rebate_application", exception);
        }
    }
}
