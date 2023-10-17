package com.numble.tracking.service;

import com.numble.tracking.domain.UrlCounter;
import com.numble.tracking.repository.UrlCounterRepository;
import java.time.LocalDate;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public interface UrlCounterService {

    @Transactional
    public void increaseCounter(String url);

}
