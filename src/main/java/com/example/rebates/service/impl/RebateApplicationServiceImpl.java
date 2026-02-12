package com.example.rebates.service.impl;

import com.example.rebates.dao.RebateApplicationDao;
import com.example.rebates.dto.RebateApplicationDbResponseDto;
import com.example.rebates.dto.RebateApplicationRequestDto;
import com.example.rebates.exception.BusinessException;
import com.example.rebates.mapper.RebateApplicationMapper;
import com.example.rebates.service.RebateApplicationService;
import com.example.rebates.vo.RebateApplicationResponseVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class RebateApplicationServiceImpl implements RebateApplicationService {

    private final RebateApplicationDao rebateApplicationDao;
    private final RebateApplicationMapper rebateApplicationMapper;

    public RebateApplicationServiceImpl(RebateApplicationDao rebateApplicationDao,
                                        RebateApplicationMapper rebateApplicationMapper) {
        this.rebateApplicationDao = rebateApplicationDao;
        this.rebateApplicationMapper = rebateApplicationMapper;
    }

    @Override
    @Transactional
    public RebateApplicationResponseVo submitRebateApplication(RebateApplicationRequestDto requestDto) {
        validateBusinessRules(requestDto);

        RebateApplicationDbResponseDto dbResponse = rebateApplicationDao.saveRebateApplication(
                rebateApplicationMapper.toDbRequest(requestDto)
        );

        if (!"SUCCESS".equalsIgnoreCase(dbResponse.statusCode())) {
            throw new BusinessException("Rebate request failed: " + dbResponse.statusMessage());
        }

        return rebateApplicationMapper.toResponseVo(dbResponse);
    }

    private void validateBusinessRules(RebateApplicationRequestDto requestDto) {
        BigDecimal maxRebatePercentage = BigDecimal.valueOf(50);
        if (requestDto.getRebatePercentage().compareTo(maxRebatePercentage) > 0) {
            throw new BusinessException("rebatePercentage cannot exceed 50");
        }
    }
}
