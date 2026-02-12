package com.example.rebates.controller;

import com.example.rebates.constants.ApiConstants;
import com.example.rebates.dto.RebateApplicationRequestDto;
import com.example.rebates.service.RebateApplicationService;
import com.example.rebates.vo.RebateApplicationResponseVo;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.API_BASE)
public class RebateApplicationController {

    private final RebateApplicationService rebateApplicationService;

    public RebateApplicationController(RebateApplicationService rebateApplicationService) {
        this.rebateApplicationService = rebateApplicationService;
    }

    @PostMapping(ApiConstants.REBATE_APPLICATIONS)
    public ResponseEntity<RebateApplicationResponseVo> submitRebateApplication(
            @Valid @RequestBody RebateApplicationRequestDto requestDto) {

        RebateApplicationResponseVo response = rebateApplicationService.submitRebateApplication(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
