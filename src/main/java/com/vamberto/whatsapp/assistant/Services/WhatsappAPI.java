package com.vamberto.whatsapp.assistant.Services;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import io.github.cdimascio.dotenv.Dotenv;

@Service
public class WhatsappAPI {

    private final WebClient webClient;

    public WhatsappAPI(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://graph.facebook.com/v22.0")
                .build();
    }

    public Mono<String> sendMessage(String token, String phoneNumberId, String to, String message) {
        String url = "/" + phoneNumberId + "/messages";

        return webClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .bodyValue(
                        """
                        {
                            "messaging_product": "whatsapp",
                            "to": "%s",
                            "type": "text",
                            "text": { "body": "%s" }
                        }
                        """.formatted(to, message)
                )
                .retrieve()
                .bodyToMono(String.class);
    }

}
