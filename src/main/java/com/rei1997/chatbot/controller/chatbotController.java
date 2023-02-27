package com.rei1997.chatbot.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rei1997.chatbot.model.lineBot.LineBotEvent;
import com.rei1997.chatbot.service.lineBot.LineBotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value="api/v1",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class chatbotController {
    private final LineBotService lineBotService;
    
    @PostMapping("/lineBotCallback")
    public String orderPayQuery(@RequestHeader(value="x-line-signature") String reqSignature ,
                                @RequestBody LineBotEvent lineBotEvent){

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        
        String jsonContent = "";
        try {
            jsonContent = mapper.writeValueAsString(lineBotEvent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if(!lineBotService.verifyLinePlatformContent(jsonContent,reqSignature)){
            return "";
        }
        System.out.println(jsonContent);


        String jsonRes="ok!";
        jsonRes=lineBotService.getTextMessage(lineBotEvent).isEmpty()?jsonRes:lineBotService.getTextMessage(lineBotEvent);

        return jsonRes;
    }

    
}