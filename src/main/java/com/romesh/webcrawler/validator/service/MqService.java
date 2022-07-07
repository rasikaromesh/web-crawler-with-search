package com.romesh.webcrawler.validator.service;

import com.romesh.webcrawler.validator.model.QueueMessageModel;
import com.romesh.webcrawler.validator.model.ValidatedUrlQueueMessageModel;
import com.romesh.webcrawler.validator.model.ValidationQueueMessageModel;
import com.romesh.webcrawler.validator.model.WebPageModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static com.romesh.webcrawler.validator.configuration.MqConfiguration.*;

@Service
public class MqService {

    @Autowired
    private RabbitTemplate template;

    public String publishedToValidatedUrlQueue(String url, int level){
        ValidatedUrlQueueMessageModel message = new ValidatedUrlQueueMessageModel(UUID.randomUUID().toString(), url, new Date(), level);
        template.convertAndSend(PAGES_EXCHANGE, VALIDATED_URL_ROUTING_KEY, message);
        System.out.println("message published to " + PAGES_EXCHANGE + "exchange with "+ VALIDATED_URL_ROUTING_KEY +"routing key");
        return "message published to " + PAGES_EXCHANGE + " exchange with "+ VALIDATED_URL_ROUTING_KEY +"routing key";
    }

    public String publishedToValidationQueue(String url, int level){
        ValidationQueueMessageModel message = new ValidationQueueMessageModel(UUID.randomUUID().toString(), url, new Date(), level);
        template.convertAndSend(PAGES_EXCHANGE, VALIDATION_ROUTING_KEY, message);
        System.out.println("message published to " + PAGES_EXCHANGE + " exchange with "+ VALIDATION_ROUTING_KEY +" routing key");
        return "message published to " + PAGES_EXCHANGE + " exchange with "+ VALIDATION_ROUTING_KEY +" routing key";
    }

    public String publishedToQueue(WebPageModel page) {
        QueueMessageModel message = new QueueMessageModel(UUID.randomUUID().toString(), page.getPages(), new Date(), page.getTitle());
        template.convertAndSend(PAGES_EXCHANGE, PAGE_ROUTING_KEY, message);
        System.out.println("message published to " + PAGES_EXCHANGE + "exchange with "+ PAGE_ROUTING_KEY +"routing key");
        return "message published to " + PAGES_EXCHANGE + " exchange with "+ PAGE_ROUTING_KEY +"routing key";
    }
}
