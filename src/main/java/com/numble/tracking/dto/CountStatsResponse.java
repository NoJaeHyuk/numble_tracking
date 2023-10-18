package com.numble.tracking.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountStatsResponse {
    private Long todayCount;
    private Long totalCount;
}
