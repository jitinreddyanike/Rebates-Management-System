package com.example.rebates.dao;

import com.example.rebates.dto.RebateApplicationDbRequestDto;
import com.example.rebates.dto.RebateApplicationDbResponseDto;

public interface RebateApplicationDao {

    RebateApplicationDbResponseDto saveRebateApplication(RebateApplicationDbRequestDto requestDto);
}
