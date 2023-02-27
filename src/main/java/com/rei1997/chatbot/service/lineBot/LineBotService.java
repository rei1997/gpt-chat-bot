package com.rei1997.chatbot.service.lineBot;


import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.rei1997.chatbot.model.lineBot.Event;
import com.rei1997.chatbot.model.lineBot.LineBotEvent;
import com.rei1997.chatbot.model.lineBot.Message;

@Service
@Configuration
public class LineBotService {
    @Value("${line.channel-secret}")
    String channelSecret;
    public boolean verifyLinePlatformContent(String requestBody, String requestSignature){
        String signature="";
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
        System.out.println("requestSignature"+requestSignature);
        System.out.println("bodySignature"+signature);
        return signature.equals(requestSignature);
    }

    public String getTextMessage(LineBotEvent lineBotEvent){
        String textMessage="";
        List<Event> events =lineBotEvent.getEvents();
        if(events.size()>1){
            return textMessage;
        }
        for (Event event : events) {
           Message message =event.getMessage();
           if(message!=null && message.getType().equals("text"))
           textMessage= message.getText();
        }

        return textMessage;
    }
}
