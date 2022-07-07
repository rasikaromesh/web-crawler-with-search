package com.romesh.webcrawler.validator.controller;

import com.romesh.webcrawler.validator.model.WebPageModel;
import com.romesh.webcrawler.validator.service.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("search")
@Slf4j
public class QueryController {

    @Autowired
    ElasticsearchService elasticsearchService;


    @GetMapping
    public ResponseEntity getAll() {
        List<WebPageModel> response = elasticsearchService.fetchAllWebPages();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("delete")
    public ResponseEntity removeAll() {
        long deletedCount = elasticsearchService.deleteAllWebPages();
        log.info("deleted record count {}", deletedCount);
        return ResponseEntity.status(HttpStatus.OK).body(deletedCount+" records deleted");
    }

    @PostMapping()
    public ResponseEntity searchByKeyword(@RequestBody String keyword) {
        List<WebPageModel> response = elasticsearchService.searchByKeyword(keyword);
        log.info("result count {}", response.size());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
