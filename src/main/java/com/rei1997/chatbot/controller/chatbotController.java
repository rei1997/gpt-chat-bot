package com.rei1997.chatbot.controller;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rei1997.chatbot.model.lineBot.LineBotEvent;
import com.rei1997.chatbot.service.lineBot.LineBotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value="chatbot-api/v1",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class chatbotController {
    private final LineBotService lineBotService;
    
    @PostMapping("/lineBotCallback")
    public ResponseEntity<String> lineBotCallback(
        @RequestHeader(value="x-line-signature") String reqSignature ,
        @RequestBody String json){

        System.out.println(json);

        if(!lineBotService.verifyLinePlatformContent(json,reqSignature)){
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        try {
            LineBotEvent lineBotEvent =mapper.readValue(json, LineBotEvent.class);
                lineBotService.getTextMessageAndReply(lineBotEvent);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("ok!");
    }

    
}