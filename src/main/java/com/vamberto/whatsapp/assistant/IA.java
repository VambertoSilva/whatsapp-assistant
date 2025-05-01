package com.vamberto.whatsapp.assistant;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatModel;
import io.github.cdimascio.dotenv.Dotenv;


public class IA {

    public static void api() {

        Dotenv dotenv = Dotenv.load();
        System.setProperty("OPENAI_API_KEY", dotenv.get("OPENAI_API_KEY"));
        //System.setProperty("OPENAI_PROJECT_ID", dotenv.get("OPENAI_PROJECT_ID"));
        //OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(dotenv.get("OPENAI_API_KEY"))
                .build();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage("voce fala portgues?")
                .model(ChatModel.GPT_4O_MINI)
                .build();
        ChatCompletion chatCompletion = client.chat().completions().create(params);

        System.out.println(chatCompletion);
    }

    public static void test(){
        Dotenv dotenv = Dotenv.load();
        System.out.println(dotenv.get("OPENAI_API_KEY"));

    }
}

