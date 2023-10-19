package com.numble.tracking.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.numble.tracking.common.exception.CustomException;
import com.numble.tracking.domain.UrlCounter;
import com.numble.tracking.facade.OptimisticLockStockFacade;
import com.numble.tracking.repository.UrlCounterRepository;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@SpringBootTest
class UrlCounterServiceConcurrencyTest {

    @Autowired
    private UrlCounterRepository urlCounterRepository;

    @Autowired
    private OptimisticLockStockFacade urlCounterService;

    //@BeforeEach
    public void insert(){
        UrlCounter urlCounter = new UrlCounter("https://jh7722.test.com/",LocalDate.now());

        urlCounterRepository.saveAndFlush(urlCounter);
    }

    @AfterEach
    public void delete() {
        urlCounterRepository.deleteAll();
    }

    @Test
    @DisplayName("동시에 100명이 특정 URL을 조회")
    void increaseCounterConcurrency() throws InterruptedException {
        AtomicInteger successCount = new AtomicInteger();
        int threadCount = 50;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    urlCounterService.increaseCounter(
                        "https://jh7722.test.com/");
                    successCount.getAndIncrement();
                } catch (ObjectOptimisticLockingFailureException oe) {
                    System.out.println("충돌감지");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Long urlCounter = urlCounterRepository.findUrlCounter("https://jh7722.test.com/",
            LocalDate.now());

        assertThat(successCount.get()).isEqualTo(threadCount);
        assertThat(urlCounter).isEqualTo(50);
    }

}