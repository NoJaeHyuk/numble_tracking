package com.numble.tracking.dto;


import com.numble.tracking.domain.UrlCounter;
import java.time.LocalDate;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UrlCounterResponse {
    private Long id;
    private String url;
    private LocalDate date;
    private int count;

    public UrlCounterResponse(UrlCounter entity) {
        this.id = entity.getId();
        this.url = entity.getUrl();
        this.date = entity.getDate();
        this.count = entity.getCount();
    }
}
