package com.example.rebates.service;

import com.example.rebates.dto.RebateApplicationRequestDto;
import com.example.rebates.vo.RebateApplicationResponseVo;

public interface RebateApplicationService {

    RebateApplicationResponseVo submitRebateApplication(RebateApplicationRequestDto requestDto);
}
