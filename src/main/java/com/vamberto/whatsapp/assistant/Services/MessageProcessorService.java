package com.vamberto.whatsapp.assistant.Services;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@Data
@RequiredArgsConstructor
public class MessageProcessorService {

    private final WhatsappAPI whatsappAPI;
    String template = "Você é um assistente financeiro. A cada mensagem do usuário, identifique a intenção principal (como \"spent\", \"balance\", \"dica_financeira\", \"earn\",) e os dados extraídos, caso não tenha informação de categoria, coloque 'Não definido'. Retorne a resposta em JSON no formato:\n" +
            "{\n" +
            "  \"intention\": \"...\",\n" +
            "  \"data\": {\n" +
            "    \"amount\": ,\n" +
            "    \"category\": ,\n" +
            "    \"date\": \"YYYY-MM-DD\"\n" +
            "  }\n" +
            "}\n" +
            "A data deve estar sempre no formato numérico (YYYY-MM-DD). Se o usuário disser \"hoje\", \"ontem\" ou \"dia 10\", converta essas expressões para a data correspondente, usando a data de hoje como referência.\n" +
            "\n" +
            "Hoje é: {{data_atual}}\n" +
            "\n" +
            "Mensagem do usuário:\n" +
            "\"{{mensagem_usuario}}\"\n" +
            "\n" +
            "Não explique nem converse, apenas retorne o JSON.\n";

    String templateTwo = "*Despesa Adicionada*\n Valor: {{amount}}\n Categoria: {{Category}}\n Data: {{date}} ";

        String dateNow = LocalDate.now().toString();



    public  void  MessageProcessor(String from, String message){

        String fullPrompt = template.replace("{{mensagem_usuario}}",message).replace("{{data_atual}}", dateNow);

        Map<String, Object> iaResponse = IaService.api(fullPrompt);
        System.out.println("🤖 Resposta da IA: " + iaResponse);System.out.println("🤖 Resposta da IA2: " + iaResponse.get("intention"));

        if("spent".equals(iaResponse.get("intention").toString())){


            Map<String, Object> data =  (Map<String, Object>) iaResponse.get("data");

            String fullResponse = templateTwo
                    .replace("{{amount}}", data.get("amount").toString())
                    .replace("{{Category}}", data.get("category").toString())
                    .replace("{{date}}", data.get("date").toString());

            whatsappAPI.sendMessage(from, fullResponse ).subscribe(
                result -> System.out.println("✅ Mensagem enviada: "),
                error -> System.err.println("❌ Erro ao enviar: ")
        );
        }


    }
}
