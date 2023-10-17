package com.numble.tracking.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UrlCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int count;

    public UrlCounter(String url, LocalDate date) {
        this.url = url;
        this.date = date;
    }

    public void increase(int count){
        this.count += count;
    }
}
