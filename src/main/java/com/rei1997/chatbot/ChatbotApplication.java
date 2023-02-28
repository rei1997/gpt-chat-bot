package com.rei1997.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import okhttp3.*;
import org.json.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
@Configuration
public class ChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatbotApplication.class, args);
	}

	// @Value("${chatgpt.token-bearer}")
	// String bearer;
	// @Bean
	// CommandLineRunner runner(){	
	// 	return args -> {
	// 		OkHttpClient client = new OkHttpClient().newBuilder().build();

	// 		// Set the prompt for the conversation
	
	// 		// Set the API parameters
	// 		JSONObject data = new JSONObject();
	// 		data.put("model",  "text-davinci-003");
	// 		data.put("prompt",  "Hello, how are you today?");
	// 		data.put("temperature", 0.7);
	// 		data.put("max_tokens", 50);
	
	// 		// Send a POST request to the chatGPT API
	// 		MediaType mediaType = MediaType.parse("application/json");
	// 		RequestBody body = RequestBody.create(data.toString(), mediaType);
	// 		Request request = new Request.Builder()
	// 				.url("https://api.openai.com/v1/completions")
	// 				.post(body)
	// 				.addHeader("Content-Type", "application/json")
	// 				.addHeader("Authorization", bearer)
	// 				.build();
	// 		Response response = client.newCall(request).execute();
	
	// 		// Parse the response and print the generated text
	// 		String jsonResponseString = new BufferedReader(new InputStreamReader(
	// 			response.body().byteStream()))
	// 			.lines().collect(
	// 				Collectors.joining("")
	// 			);
	// 		System.out.println(jsonResponseString);
	// 		JSONObject jsonResponse = new JSONObject(jsonResponseString);
	// 		System.out.println("Generated text: " + jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text").trim());
			

	// 	};
	// }
}
