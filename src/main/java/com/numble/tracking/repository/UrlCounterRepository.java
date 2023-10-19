package com.numble.tracking.repository;

import com.numble.tracking.domain.UrlCounter;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlCounterRepository extends JpaRepository<UrlCounter, Long> {

    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Lock(LockModeType.OPTIMISTIC)
    Optional<UrlCounter> findByUrlAndDate(String url, LocalDate date);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "update UrlCounter u set u.count = u.count + 1 where u.id = :urlCountId")
    void increaseUrlCount(Long urlCountId);

    @Query("SELECT u.count FROM UrlCounter u WHERE u.url = :url AND u.date = :date")
    Long findUrlCounter(@Param("url") String url, @Param("date") LocalDate date);

    @Query("select sum(u.count) from UrlCounter u where u.url = ?1")
    Long findTotalCountByUrl(String url);

    @Query("SELECT u FROM UrlCounter u WHERE u.url = :url AND u.date BETWEEN :startDate AND :endDate ORDER BY u.date")
    List<UrlCounter> findWeekDataByUrlAndDateRange(@Param("url") String url,
        @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
