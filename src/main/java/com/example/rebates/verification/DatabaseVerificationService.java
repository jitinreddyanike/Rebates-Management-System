package com.example.rebates.verification;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

@Service
public class DatabaseVerificationService {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseVerificationService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DbArtifactsStatusVo verifyArtifacts() {
        return jdbcTemplate.execute((ConnectionCallback<DbArtifactsStatusVo>) connection -> {
            DatabaseMetaData metaData = connection.getMetaData();
            boolean tableExists = objectExists(metaData.getTables(null, null, "REBATE_APPLICATION_AUDIT", new String[]{"TABLE"}));
            boolean routineExists = objectExists(metaData.getFunctions(null, null, "SP_SUBMIT_REBATE_APPLICATION"));

            String message;
            if (tableExists && routineExists) {
                message = "H2 artifacts are available";
            } else {
                message = "One or more H2 artifacts are missing";
            }

            return new DbArtifactsStatusVo(
                    tableExists,
                    routineExists,
                    metaData.getDatabaseProductName(),
                    message
            );
        });
    }

    private boolean objectExists(ResultSet resultSet) {
        try (ResultSet rs = resultSet) {
            return rs.next();
        } catch (Exception exception) {
            return false;
        }
    }
}
