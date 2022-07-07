package com.romesh.webcrawler.validator.model;

import java.util.Date;

public class ValidationQueueMessageModel {

    private String messageId;
    private String message;
    private Date messageDate;

    private int level;

    public ValidationQueueMessageModel(String messageId, String message, Date messageDate, int level) {
        this.messageId = messageId;
        this.message = message;
        this.messageDate = messageDate;
        this.level = level;
    }

    public ValidationQueueMessageModel() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "ValidationQueueMessage{" +
                "messageId='" + messageId + '\'' +
                ", message='" + message + '\'' +
                ", messageDate=" + messageDate +
                ", level=" + level +
                '}';
    }
}
