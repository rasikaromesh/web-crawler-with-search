package com.romesh.webcrawler.validator.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfiguration {


    public static final String PAGE_QUEUE = "page_queue";
    public static final String VALIDATION_QUEUE = "validation_queue";
    public static final String VALIDATED_URL_QUEUE = "validated_url_queue";

    public static final String PAGES_EXCHANGE = "pages_exchange";

    public static final String PAGE_ROUTING_KEY = "page_routing_key";
    public static final String VALIDATION_ROUTING_KEY = "validation_routing_key";

    public static final String VALIDATED_URL_ROUTING_KEY = "validated_url_routing_key";


    @Bean
    public Queue queue(){
        return new Queue(PAGE_QUEUE);
    }

    @Bean
    public Queue validationQueue(){
        return new Queue(VALIDATION_QUEUE);
    }

    @Bean Queue validatedUrlQueue(){
        return new Queue(VALIDATED_URL_QUEUE);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(PAGES_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(PAGE_ROUTING_KEY);
    }

    @Bean
    public Binding validationQueueBinding(@Qualifier("validationQueue") Queue validationQueue, TopicExchange exchange){
        return BindingBuilder
                .bind(validationQueue)
                .to(exchange)
                .with(VALIDATION_ROUTING_KEY);
    }

    @Bean
    public Binding validatedUrlQueueBinding(@Qualifier("validatedUrlQueue") Queue validatedUrlQueue, TopicExchange exchange){
        return BindingBuilder
                .bind(validatedUrlQueue)
                .to(exchange)
                .with(VALIDATED_URL_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
