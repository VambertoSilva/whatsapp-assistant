package com.vamberto.whatsapp.assistant.Controllers;

import com.vamberto.whatsapp.assistant.Services.IaService;
import com.vamberto.whatsapp.assistant.Services.MessageProcessorService;
import com.vamberto.whatsapp.assistant.Services.WhatsappAPI;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@Data
public class WhatsappController {

    Dotenv dotenv = Dotenv.load();
    private final WhatsappAPI whatsappAPI;
    private final MessageProcessorService messageProcessor;
    private final String VERIFY_TOKEN = "pode-rodar";
    private final String to = "5581994967304";


    @GetMapping("/enviar")
    public ResponseEntity<String> enviar() {
        String token = dotenv.get("TOKEN_CLOUD_API") ;
        String phoneNumberId = dotenv.get("PHONE_NUMBER_ID");
        String message = "hi";

         Mono<String> response = whatsappAPI.sendMessage(to, message);

         return ResponseEntity.ok(response.block());


    }

    @GetMapping("/webhook")
    public ResponseEntity<String> verificarWebhook(
            @RequestParam(name = "hub.mode") String mode,
            @RequestParam(name = "hub.verify_token") String token,
            @RequestParam(name = "hub.challenge") String challenge) {

        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
            System.out.println(challenge);
            System.out.println(token);
            return ResponseEntity.ok(challenge); // devolve o challenge se o token estiver certo
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> recive(@RequestBody Map<String, Object> payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.convertValue(payload, JsonNode.class);

            JsonNode entryNode = root.path("entry");
            if (!entryNode.isArray() || entryNode.isEmpty()) return ResponseEntity.ok().build();

            JsonNode changesNode = entryNode.get(0).path("changes");
            if (!changesNode.isArray() || changesNode.isEmpty()) return ResponseEntity.ok().build();

            JsonNode valueNode = changesNode.get(0).path("value");
            JsonNode messagesNode = valueNode.path("messages");

            if (!messagesNode.isArray() || messagesNode.isEmpty()) {
                return ResponseEntity.ok().build();
            }

            JsonNode messageObj = messagesNode.get(0);
            String message = messageObj.path("text").path("body").asText();
            String from = messageObj.path("from").asText(); // número que enviou a mensagem

            System.out.println("📩 Mensagem recebida: " + message + " numero: " + from);

            messageProcessor.MessageProcessor(from, message);



        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }

}
