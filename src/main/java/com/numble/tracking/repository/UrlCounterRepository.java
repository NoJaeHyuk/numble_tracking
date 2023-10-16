package com.numble.tracking.repository;

import com.numble.tracking.domain.UrlCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlCounterRepository extends JpaRepository<UrlCounter, Long> {

}
