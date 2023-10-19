package com.numble.tracking.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "url_counter",
    uniqueConstraints = @UniqueConstraint(columnNames = {"url", "date"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UrlCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String url;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private int count;

    @Version
    private int version;

    public UrlCounter(long id, String url, LocalDate date, int count) {
        this.id = id;
        this.url = url;
        this.date = date;
        this.count = count;
    }

    public UrlCounter(String url, LocalDate date) {
        this.url = url;
        this.date = date;
        this.count += 1;
    }

    public void increase(int count){
        this.count += count;
    }
}
