package com.example.rebates.mapper;

import com.example.rebates.dto.RebateApplicationDbRequestDto;
import com.example.rebates.dto.RebateApplicationDbResponseDto;
import com.example.rebates.dto.RebateApplicationRequestDto;
import com.example.rebates.vo.RebateApplicationResponseVo;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class RebateApplicationMapper {

    public RebateApplicationDbRequestDto toDbRequest(RebateApplicationRequestDto requestDto) {
        return new RebateApplicationDbRequestDto(
                requestDto.getVendorId(),
                requestDto.getVendorName(),
                requestDto.getProductCode(),
                requestDto.getQuantity(),
                requestDto.getUnitPrice(),
                requestDto.getRebatePercentage(),
                requestDto.getRemarks()
        );
    }

    public RebateApplicationResponseVo toResponseVo(RebateApplicationDbResponseDto dbResponse) {
        RebateApplicationResponseVo responseVo = new RebateApplicationResponseVo();
        responseVo.setTransactionId(UUID.randomUUID().toString());
        responseVo.setApplicationId(dbResponse.applicationId());
        responseVo.setRebateAmount(dbResponse.rebateAmount());
        responseVo.setStatus(dbResponse.statusCode());
        responseVo.setMessage(dbResponse.statusMessage());
        responseVo.setProcessedAt(OffsetDateTime.now());
        return responseVo;
    }
}
