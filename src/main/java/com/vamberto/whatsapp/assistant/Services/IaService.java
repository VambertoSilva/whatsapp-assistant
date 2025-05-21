package com.vamberto.whatsapp.assistant.Services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatModel;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;


public class IaService {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Map<String, Object> api(String message) {

        Map<String, Object> map = null;

        Dotenv dotenv = Dotenv.load();
        System.setProperty("OPENAI_API_KEY", dotenv.get("OPENAI_API_KEY"));

        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(dotenv.get("OPENAI_API_KEY"))
                .build();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage(message)
                .model(ChatModel.GPT_4O_MINI)
                .build();

        ChatCompletion result = client.chat().completions().create(params);

        // Pegando o conteúdo da primeira resposta
        String iaResponse = result.choices().get(0).message().content().get();

        System.out.println("Valor antes de virar json: " + iaResponse);

        try {
         map = mapper.readValue(iaResponse, new TypeReference<Map<String, Object>>() {
        });
        } catch (JsonProcessingException e) {
            System.out.println("Erro ao processar JSON: " + e.getMessage());
        }

        return map;
    }

    public static void test(){
        Dotenv dotenv = Dotenv.load();
        System.out.println(dotenv.get("OPENAI_API_KEY"));

    }
}
