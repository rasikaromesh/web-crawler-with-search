package com.romesh.webcrawler.validator.service;

import com.romesh.webcrawler.validator.model.ValidatedUrlQueueMessageModel;
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
}
