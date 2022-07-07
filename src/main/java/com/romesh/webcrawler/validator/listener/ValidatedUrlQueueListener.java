package com.romesh.webcrawler.validator.listener;

import com.romesh.webcrawler.validator.configuration.MqConfiguration;
import com.romesh.webcrawler.validator.model.ValidatedUrlQueueMessageModel;
import com.romesh.webcrawler.validator.model.WebPageModel;
import com.romesh.webcrawler.validator.service.CrawlerService;
import com.romesh.webcrawler.validator.service.MqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class ValidatedUrlQueueListener {

    @Autowired
    CrawlerService crawlerService;

    @Autowired
    MqService mqService;

    @RabbitListener(queues = MqConfiguration.VALIDATED_URL_QUEUE)
    public void receive(ValidatedUrlQueueMessageModel message){
        log.info("queue message {}", message);
        crawlerService.crawl(message.getLevel(), message.getUrl());
        try {
            CompletableFuture<WebPageModel> webPageModelCompletableFuture = crawlerService.crawl(message.getLevel(), message.getUrl());
            if (null!= webPageModelCompletableFuture){
                mqService.publishedToQueue(webPageModelCompletableFuture.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
