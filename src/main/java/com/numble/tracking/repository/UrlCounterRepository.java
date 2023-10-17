package com.numble.tracking.repository;

import com.numble.tracking.domain.UrlCounter;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlCounterRepository extends JpaRepository<UrlCounter, Long> {

    Optional<UrlCounter> findByUrlAndDate(String url, LocalDate date);

}
