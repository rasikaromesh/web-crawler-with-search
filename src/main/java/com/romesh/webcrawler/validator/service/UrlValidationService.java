package com.romesh.webcrawler.validator.service;

import com.romesh.webcrawler.validator.util.UrlStore;
import org.springframework.stereotype.Service;

@Service
public class UrlValidationService {

    public boolean validateUrl(String url){
        return UrlStore.URL_LIST.contains(url);
    }
}
