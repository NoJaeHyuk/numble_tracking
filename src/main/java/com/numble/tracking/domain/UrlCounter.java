package com.numble.tracking.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    /*@Version
    private Long version;*/

    public UrlCounter(String url, LocalDate date) {
        this.url = url;
        this.date = date;
    }

    /*public UrlCounter(long id, String url, LocalDate date, int count) {
        this.id = id;
        this.url = url;
        this.date = date;
        this.count = count;
    }*/

    public void increase(int count){
        this.count += count;
    }
}
