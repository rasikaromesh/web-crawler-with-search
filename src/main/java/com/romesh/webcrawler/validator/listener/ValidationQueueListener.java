package com.romesh.webcrawler.validator.listener;

import com.romesh.webcrawler.validator.configuration.MqConfiguration;
import com.romesh.webcrawler.validator.model.ValidationQueueMessageModel;
import com.romesh.webcrawler.validator.service.MqService;
import com.romesh.webcrawler.validator.service.UrlValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValidationQueueListener {

    @Autowired
    UrlValidationService urlValidationService;

    @Autowired
    MqService mqService;

    @RabbitListener(queues = MqConfiguration.VALIDATION_QUEUE)
    public void receive(ValidationQueueMessageModel message){
        log.info("queue message {}", message);
        String url = message.getMessage();
        if (!urlValidationService.validateUrl(url)){
            mqService.publishedToValidatedUrlQueue(url, message.getLevel());
            log.info("published to Queue");
        }


    }
}
