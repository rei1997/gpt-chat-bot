package com.rei1997.chatbot.service.lineBot;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.rei1997.chatbot.model.lineBot.Event;
import com.rei1997.chatbot.model.lineBot.LineBotEvent;
import com.rei1997.chatbot.model.lineBot.Message;

@Service
@Configuration
public class LineBotService {
    @Value("${line.channel.secret}")
    String channelSecret;
    @Value("${line.channel.access-token}")
    String channelAccessToken;  

    public boolean verifyLinePlatformContent(String requestBody, String requestSignature) {
        String signature = "";
        SecretKeySpec key = new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256");
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(key);
            byte[] source = requestBody.getBytes("UTF-8");
            signature = Base64.encodeBase64String(mac.doFinal(source));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Compare x-line-signature request header string and the signature
        System.out.println("bodySignature.equals(headerSignature)= " + signature.equals(requestSignature));
        return signature.equals(requestSignature);
    }

    public void getTextMessageAndReply(LineBotEvent lineBotEvent) {
        String textMessage = "";
        String replyToken = "";
        boolean textFound = false;

        List<Event> events = lineBotEvent.getEvents();
        // only deal with text message
        for (Event event : events) {
            if (textFound) {
                break;
            }
            replyToken = event.getReplyToken();
            Message message = event.getMessage();
            if (message != null && message.getType().equals("text"))
                textMessage = message.getText();
            textFound = true;
        }
        replyToLineClient(replyToken,textMessage);
    }

    private void replyToLineClient(String replyToken, String replyMessange) {
        LineMessagingClient client = LineMessagingClient.builder(channelAccessToken).build();

        TextMessage textMessage = new TextMessage(replyMessange);
        ReplyMessage replyMessage = new ReplyMessage(replyToken,textMessage);

        BotApiResponse botApiResponse;
        try {
            botApiResponse = client.replyMessage(replyMessage).get();
            System.out.println(botApiResponse);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

}
