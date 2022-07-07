package com.romesh.webcrawler.validator.service;

import com.romesh.webcrawler.validator.model.WebPageModel;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CrawlerService {

    private static final int MAX_DEPTH = 5;//TODO

    @Autowired
    MqService mqService;

    @Async
    public synchronized CompletableFuture<WebPageModel> crawl(int level, String url){
        log.info("crawling for {} with level {}", url, level);
        if (level<= MAX_DEPTH){
            Document document = request(url);
            if (document != null){
                System.out.println(document.text());
                for (Element link: document.select("a[href]")){
                    String nextUrl = link.absUrl("href");
                    if (level <= MAX_DEPTH && isProperUrl(nextUrl)){
                        mqService.publishedToValidationQueue(nextUrl, ++level);
                    }
                }
                return CompletableFuture.completedFuture(new WebPageModel(document.title(),document.body().text()));
            }
            return null;
        }
        return null;
    }

    private Document request(String url){
        System.out.println("the url - "+url);
        try {
            Connection con = Jsoup.connect(url);
            Document document = con.get();
            if (con.response().statusCode()== HttpStatus.OK.value()){
                System.out.println("Thread ID:"+ " Received web page at "+ url);
                String title = document.title();
                System.out.println(title);
                return document;
            }
            return null;
        } catch (IOException e){
            e.printStackTrace();//TODO remove
            return null;
        }
    }

    private boolean isProperUrl(String url){
        return (url.startsWith("http://") || url.startsWith("https://"));
    }
}
