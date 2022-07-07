package com.romesh.webcrawler.validator.model;

import java.util.Date;

public class ValidatedUrlQueueMessageModel {
    private String messageId;
    private String url;
    private Date messageDate;

    private int level;

    public ValidatedUrlQueueMessageModel(String messageId, String url, Date messageDate, int level) {
        this.messageId = messageId;
        this.url = url;
        this.messageDate = messageDate;
        this.level = level;
    }

    public ValidatedUrlQueueMessageModel() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        return "ValidatedUrlQueueMessage{" +
                "messageId='" + messageId + '\'' +
                ", url='" + url + '\'' +
                ", messageDate=" + messageDate +
                ", level=" + level +
                '}';
    }
}
