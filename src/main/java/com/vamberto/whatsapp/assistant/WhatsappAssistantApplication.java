package com.vamberto.whatsapp.assistant;

import com.vamberto.whatsapp.assistant.Configs.DotenvConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WhatsappAssistantApplication {

	public static void main(String[] args) {
		DotenvConfig.loadEnv();
		SpringApplication.run(WhatsappAssistantApplication.class, args);


	}

}
