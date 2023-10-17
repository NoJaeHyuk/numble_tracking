package com.numble.tracking.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlCounterServiceTest {

    @Autowired
    private UrlCounterService urlCounterService;

    @Test
    @DisplayName("URL 조회수 증가 테스트")
    void increaseCounter() {
        urlCounterService.increaseCounter("https://jh7722.tistory.com/");
    }
}