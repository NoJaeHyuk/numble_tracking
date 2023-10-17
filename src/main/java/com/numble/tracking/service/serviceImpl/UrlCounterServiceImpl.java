package com.numble.tracking.service.serviceImpl;

import com.numble.tracking.domain.UrlCounter;
import com.numble.tracking.repository.UrlCounterRepository;
import com.numble.tracking.service.UrlCounterService;
import java.time.LocalDate;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlCounterServiceImpl implements UrlCounterService {

    public static final int COUNT = 1;

    private final Logger LOGGER = LoggerFactory.getLogger(UrlCounterServiceImpl.class);
    private final UrlCounterRepository urlCounterRepository;

    @Override
    @Transactional
    public void increaseCounter(String url) {
        LocalDate today = LocalDate.now();

        UrlCounter counter = urlCounterRepository.findByUrlAndDate(url, today)
            .orElse(new UrlCounter(url, today));

        LOGGER.info("[increaseCounter before] url : {}, count : {}", counter.getUrl(), counter.getCount());

        counter.increase(COUNT);

        UrlCounter result = urlCounterRepository.saveAndFlush(counter);

        LOGGER.info("[increaseCounter after] url : {}, count : {}", result.getUrl(), result.getCount());
    }
}
