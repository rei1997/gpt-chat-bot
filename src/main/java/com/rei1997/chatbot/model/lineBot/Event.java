package com.rei1997.chatbot.model.lineBot;

public class Event {
    private String type;
    private Message message;
    private long timestamp;
    private Source source;
    private String replyToken;
    private String mode;
    private String webhookEventId;
    private DeliveryContext deliveryContext;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getReplyToken() {
        return replyToken;
    }

    public void setReplyToken(String replyToken) {
        this.replyToken = replyToken;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getWebhookEventId() {
        return webhookEventId;
    }

    public void setWebhookEventId(String webhookEventId) {
        this.webhookEventId = webhookEventId;
    }

    public DeliveryContext getDeliveryContext() {
        return deliveryContext;
    }

    public void setDeliveryContext(DeliveryContext deliveryContext) {
        this.deliveryContext = deliveryContext;
    }

}
