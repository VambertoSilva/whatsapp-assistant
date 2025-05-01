package com.vamberto.whatsapp.assistant.Controllers;

import com.vamberto.whatsapp.assistant.Services.WhatsappAPI;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.net.http.HttpResponse;
import java.util.Map;

@Controller
@Data
public class WhatsappController {

    Dotenv dotenv = Dotenv.load();
    private final WhatsappAPI whatsappAPI;
    private final String VERIFY_TOKEN = "pode-rodar";


    @GetMapping("/enviar")
    public ResponseEntity<String> enviar() {
        String token = dotenv.get("TOKEN_CLOUD_API") ;
        String phoneNumberId = dotenv.get("PHONE_NUMBER_ID");
        String to = "5581994967304";
        String message = "hi";

         Mono<String> response = whatsappAPI.sendMessage(token, phoneNumberId, to, message);

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
    public ResponseEntity<Void> recive(@RequestBody Map<String, Object> payload){
        System.out.println(payload);
        return ResponseEntity.ok().build();
    }
}
