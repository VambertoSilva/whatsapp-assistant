package com.vamberto.whatsapp.assistant.Services;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Data
@RequiredArgsConstructor
public class MessageProcessorService {

    private final WhatsappAPI whatsappAPI;
    String template = "Você é um assistente financeiro. A cada mensagem do usuário, identifique a intenção principal (como \"registrar_gasto\", \"consultar_saldo\", \"dica_financeira\", \"adicionar_saldo\",) e os dados extraídos. Retorne a resposta em JSON no formato:\n" +
            "{\n" +
            "  \"intencao\": \"...\",\n" +
            "  \"dados\": {\n" +
            "    \"valor\": ,\n" +
            "    \"categoria\": ,\n" +
            "    \"data\": \"YYYY-MM-DD\"\n" +
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

        String dateNow = LocalDate.now().toString();



    public  void  MessageProcessor(String from, String message){

        String fullPrompt = template.replace("{{mensagem_usuario}}",message).replace("{{data_atual}}", dateNow);

        String iaResponse = IaService.api(fullPrompt);
        //System.out.println("🤖 Resposta da IA: " + iaResponse);

        whatsappAPI.sendMessage(from, iaResponse).subscribe(
                result -> System.out.println("✅ Mensagem enviada: "),
                error -> System.err.println("❌ Erro ao enviar: ")
        );

    }
}
