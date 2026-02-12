package com.example.rebates.verification;

import com.example.rebates.constants.ApiConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.API_BASE)
public class DatabaseVerificationController {

    private final DatabaseVerificationService databaseVerificationService;

    public DatabaseVerificationController(DatabaseVerificationService databaseVerificationService) {
        this.databaseVerificationService = databaseVerificationService;
    }

    @GetMapping("/rebates/db/artifacts")
    public ResponseEntity<DbArtifactsStatusVo> verifyDatabaseArtifacts() {
        return ResponseEntity.ok(databaseVerificationService.verifyArtifacts());
    }
}
