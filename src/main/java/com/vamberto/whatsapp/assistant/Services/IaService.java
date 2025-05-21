package com.vamberto.whatsapp.assistant.Services;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatModel;
import io.github.cdimascio.dotenv.Dotenv;

public class IaService {

    public static String api(String message) {


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
        return result.choices().get(0).message().content().get();
    }

    public static void test(){
        Dotenv dotenv = Dotenv.load();
        System.out.println(dotenv.get("OPENAI_API_KEY"));

    }
}
