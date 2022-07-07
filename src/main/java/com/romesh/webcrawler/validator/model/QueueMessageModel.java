package com.romesh.webcrawler.validator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueueMessageModel {

    private String messageId;
    private String message;
    private Date messageDate;

    private String title;

}
