package com.numble.tracking.service;

import com.numble.tracking.common.exception.CustomException;
import com.numble.tracking.dto.CountStatsResponse;
import com.numble.tracking.dto.UrlCounterResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UrlCounterService {

    UrlCounterResponse increaseCounter(String url);

    CountStatsResponse getStats(String url);

    List<UrlCounterResponse> getWeeklyStats(String url);
}