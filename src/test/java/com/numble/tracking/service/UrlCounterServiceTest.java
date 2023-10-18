package com.numble.tracking.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import com.numble.tracking.common.exception.CustomException;
import com.numble.tracking.domain.UrlCounter;
import com.numble.tracking.dto.CountStatsResponse;
import com.numble.tracking.dto.UrlCounterResponse;
import com.numble.tracking.repository.UrlCounterRepository;
import com.numble.tracking.service.serviceImpl.UrlCounterServiceImpl;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({UrlCounterServiceImpl.class})
class UrlCounterServiceTest {

    @MockBean
    private UrlCounterRepository urlCounterRepository;

    @Autowired
    private UrlCounterService urlCounterService;

    @Test
    @DisplayName("URL 조회수 증가 테스트")
    void increaseCounter() {
        // given
        Mockito.when(urlCounterRepository.save(any(UrlCounter.class)))
            .then(returnsFirstArg());

        // when
        UrlCounterResponse urlCounterResponse = urlCounterService.increaseCounter(
            "https://jh7722.test.com/");

        // then
        assertThat(urlCounterResponse.getUrl()).isEqualTo("https://jh7722.test.com/");
        assertThat(urlCounterResponse.getCount()).isEqualTo(1);

        verify(urlCounterRepository).save(any());
    }

    @Test
    @DisplayName("URL 일간/누적 조회수 조회 테스트")
    void getStats() {
        // given
        UrlCounter givenUrlCount1
            = new UrlCounter(120L, "https://jh7722.test.com/", LocalDate.now().minusDays(5), 100);

        UrlCounter givenUrlCount2
            = new UrlCounter(123L, "https://jh7722.test.com/", LocalDate.now(), 100);

        Mockito.when(
                urlCounterRepository.findUrlCounter("https://jh7722.test.com/", LocalDate.now()))
            .thenReturn((long) givenUrlCount2.getCount());

        Mockito.when(urlCounterRepository.findTotalCountByUrl("https://jh7722.test.com/"))
            .thenReturn((long) givenUrlCount1.getCount() + givenUrlCount2.getCount());

        // when
        CountStatsResponse stats = urlCounterService.getStats("https://jh7722.test.com/");

        // then
        assertThat(stats.getTodayCount()).isEqualTo((long) givenUrlCount2.getCount());
        assertThat(stats.getTotalCount()).isEqualTo(
            (long) givenUrlCount1.getCount() + givenUrlCount2.getCount());

        verify(urlCounterRepository).findUrlCounter("https://jh7722.test.com/", LocalDate.now());
        verify(urlCounterRepository).findTotalCountByUrl("https://jh7722.test.com/");
    }

    @Test
    @DisplayName("URL 7일간의 일간 조회수 조회 테스트")
    void getWeeklyStats() {
        // given
        UrlCounter givenUrlCount1
            = new UrlCounter(120L, "https://jh7722.test.com/", LocalDate.now().minusDays(7), 100);

        UrlCounter givenUrlCount2
            = new UrlCounter(123L, "https://jh7722.test.com/", LocalDate.now(), 100);

        List<UrlCounter> urlCounterList = new ArrayList<>();
        urlCounterList.add(givenUrlCount1);
        urlCounterList.add(givenUrlCount2);

        Mockito.when(
                urlCounterRepository.findWeekDataByUrlAndDateRange("https://jh7722.test.com/",
                    LocalDate.now().minusDays(7), LocalDate.now()))
            .thenReturn(urlCounterList);

        // when
        List<UrlCounterResponse> weeklyStats = urlCounterService.getWeeklyStats(
            "https://jh7722.test.com/");

        // then
        assertThat(weeklyStats.size()).isEqualTo(2);

        verify(urlCounterRepository).findWeekDataByUrlAndDateRange("https://jh7722.test.com/",
            LocalDate.now().minusDays(7), LocalDate.now());
    }
}