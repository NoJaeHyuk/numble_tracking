package com.numble.tracking.facade;

import com.numble.tracking.dto.UrlCounterResponse;
import com.numble.tracking.service.UrlCounterService;
import org.springframework.stereotype.Component;

@Component
public class OptimisticLockStockFacade {

    private UrlCounterService urlCounterService;

    public OptimisticLockStockFacade(UrlCounterService urlCounterService) {
        this.urlCounterService = urlCounterService;
    }

    public UrlCounterResponse increaseCounter(String url) throws InterruptedException {
        while (true) {
            try {
                UrlCounterResponse urlCounterResponse = urlCounterService.increaseCounter(url);

                return urlCounterResponse;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
