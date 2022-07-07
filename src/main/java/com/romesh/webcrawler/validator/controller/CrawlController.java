package com.romesh.webcrawler.validator.controller;

import com.romesh.webcrawler.validator.model.SearchRequestModel;
import com.romesh.webcrawler.validator.model.WebPageModel;
import com.romesh.webcrawler.validator.service.ElasticsearchService;
import com.romesh.webcrawler.validator.service.MqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("crawl")
@Slf4j
public class CrawlController {

    @Autowired
    private MqService mqService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @PostMapping()
    public ResponseEntity crawl(@RequestBody String[] urls) {
        for (String url: urls) {
            mqService.publishedToValidationQueue(url, 1);
        }
        return ResponseEntity.status(HttpStatus.OK).body("Published");
    }

    @PostMapping(path = "search")
    public List<WebPageModel> searchByKeyword(@RequestBody SearchRequestModel request){
        log.info("start a search for {}", request);
        return elasticsearchService.searchByKeyword(request.getKeyword());

    }
    @GetMapping
    public ResponseEntity getAll() {
        List<WebPageModel> response = elasticsearchService.fetchAllWebPages();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping
    public ResponseEntity removeAll() {
        long deletedCount = elasticsearchService.deleteAllWebPages();
        log.info("deleted record count {}", deletedCount);
        return ResponseEntity.status(HttpStatus.OK).body(deletedCount+" records deleted");
    }
}
