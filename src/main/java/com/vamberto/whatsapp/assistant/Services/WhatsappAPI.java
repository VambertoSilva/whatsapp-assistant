package com.vamberto.whatsapp.assistant.Services;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;

@Service
public class WhatsappAPI {

    Dotenv dotenv = Dotenv.load();
    private final WebClient webClient;

    public WhatsappAPI(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://graph.facebook.com/v22.0")
                .build();
    }

    public Mono<String> sendMessage(String to, String message) {
        String token = dotenv.get("TOKEN_CLOUD_API");
        String phoneNumberId = dotenv.get("PHONE_NUMBER_ID");

        String url = "/" + phoneNumberId + "/messages";

        Map<String, Object> text = Map.of("body", message);
        Map<String, Object> payload = Map.of(
                "messaging_product", "whatsapp",
                "to", to,
                "type", "text",
                "text", text
        );

        return webClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .bodyValue(payload)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> {
                                    System.err.println("Erro completo da API:\n" + body);
                                    return Mono.error(new RuntimeException("Erro API: " + body));
                                })
                )
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.error(new RuntimeException("Erro ao enviar mensagem", e)));
    }




}
