package com.numble.tracking.service;

import org.springframework.stereotype.Service;

@Service
public interface UrlCounterService {

    public void increaseCounter(String url);

}
