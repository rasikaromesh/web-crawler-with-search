package com.romesh.webcrawler.validator.service;

import com.romesh.webcrawler.validator.model.WebPageModel;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

@Service
@Slf4j
public class ElasticsearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    public List<WebPageModel> searchByKeyword(String keyword){
        log.info("start a search for {}",keyword);
        QueryBuilder query = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchPhraseQuery("title", "*"+keyword+"*").slop(1))
                .should(QueryBuilders.matchPhraseQuery("pages", "*"+keyword+"*").slop(1));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();

        return elasticsearchRestTemplate.search(searchQuery, WebPageModel.class)
                .get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public List<WebPageModel> fetchAllWebPages(){
        QueryBuilder query = QueryBuilders.boolQuery()
                .should(
                        QueryBuilders.queryStringQuery("*")
                                .lenient(true)
                                .field("pages")
                );
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();
        return elasticsearchRestTemplate.search(searchQuery, WebPageModel.class)
                .get()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
    }

    public long deleteAllWebPages(){
        QueryBuilder query = QueryBuilders.boolQuery()
                .should(
                        QueryBuilders.queryStringQuery("*")
                                .lenient(true)
                                .field("pages")
                );
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();
        return elasticsearchRestTemplate.delete(searchQuery, WebPageModel.class).getTotal();
    }
}
