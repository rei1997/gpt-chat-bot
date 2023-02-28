package com.rei1997.chatbot.service.chatGPT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.json.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
@Configuration
public class ChatGptService {

	@Value("${chatgpt.token-bearer}")
	String bearer;
	@Value("${chatgpt.api-url}")
	String chatGptApiUrl;
	OkHttpClient client = new OkHttpClient().newBuilder()
	.connectTimeout(10, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .readTimeout(180, TimeUnit.SECONDS)
	.build();

	public String callChatGpt(String question) throws JSONException{
		String chatGptSays = "";
		// Set the API parameters
		JSONObject data = new JSONObject();

		data.put("model", "text-davinci-003");
		data.put("prompt", question);
		data.put("temperature", 0.3);
		data.put("max_tokens", 4000);

		// Send a POST request to the chatGPT API
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(data.toString(), mediaType);
		Request request = new Request.Builder()
				.url(chatGptApiUrl)
				.post(body)
				.addHeader("Content-Type", "application/json")
				.addHeader("Authorization", bearer)
				.build();
				
		try (Response response = client.newCall(request).execute()) {
			// Parse the response and print the generated text
			String jsonResponseString = new BufferedReader(new InputStreamReader(
					response.body().byteStream()))
					.lines().collect(Collectors.joining("\n"));
			System.out.println("chatGptApi response: "+jsonResponseString);
			JSONObject jsonResponse = new JSONObject(jsonResponseString);
			chatGptSays=jsonResponse.getJSONArray("choices").getJSONObject(0).getString("text").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chatGptSays;
	}
}
