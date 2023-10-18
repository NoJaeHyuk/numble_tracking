package com.numble.tracking.service.serviceImpl;

import com.numble.tracking.common.Constants.ExceptionClass;
import com.numble.tracking.common.exception.CustomException;
import com.numble.tracking.domain.UrlCounter;
import com.numble.tracking.dto.CountStatsResponse;
import com.numble.tracking.dto.UrlCounterResponse;
import com.numble.tracking.repository.UrlCounterRepository;
import com.numble.tracking.service.UrlCounterService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrlCounterServiceImpl implements UrlCounterService {

    public static final int COUNT = 1;
    public static final LocalDate TODAY = LocalDate.now();
    public static final LocalDate BEFOREDAY = TODAY.minusDays(7);

    private final Logger LOGGER = LoggerFactory.getLogger(UrlCounterServiceImpl.class);
    private final UrlCounterRepository urlCounterRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UrlCounterResponse increaseCounter(String url) {
        UrlCounter counter = urlCounterRepository.findByUrlAndDate(url, TODAY)
            .orElse(new UrlCounter(url, TODAY));

        LOGGER.info("[increaseCounter before] url : {}, count : {}", counter.getUrl(),
            counter.getCount());

        counter.increase(COUNT);

        UrlCounter result = urlCounterRepository.save(counter);

        LOGGER.info("[increaseCounter after] url : {}, count : {}", result.getUrl(),
            result.getCount());

        return new UrlCounterResponse(result);
    }

    @Override
    public CountStatsResponse getStats(String url) {
        LOGGER.info("[getStats] url : {}", url);

        return new CountStatsResponse(urlCounterRepository.findUrlCounter(url, TODAY),
            urlCounterRepository.findTotalCountByUrl(url));
    }

    @Override
    public List<UrlCounterResponse> getWeeklyStats(String url) {
        LOGGER.info("[getWeeklyStats] url : {}", url);

        return convertEntityListToDTOList(urlCounterRepository.findWeekDataByUrlAndDateRange(
            url, BEFOREDAY, TODAY));
    }

    private List<UrlCounterResponse> convertEntityListToDTOList(List<UrlCounter> entities) {
        return entities.stream()
            .map(this::convertEntityToDTO)
            .collect(Collectors.toList());
    }

    private UrlCounterResponse convertEntityToDTO(UrlCounter entity) {
        return new UrlCounterResponse(entity);
    }
}
