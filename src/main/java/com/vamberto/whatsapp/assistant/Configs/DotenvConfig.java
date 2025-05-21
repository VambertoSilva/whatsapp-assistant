package com.vamberto.whatsapp.assistant.Configs;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class DotenvConfig {

    public static void loadEnv(){
        Dotenv dotenv = Dotenv.load();

        System.setProperty(
                "DB_URL",
                Objects.requireNonNull(dotenv.get("DB_URL")));
        System.setProperty(
                "DB_USERNAME",
                Objects.requireNonNull(dotenv.get("DB_USERNAME")));
        System.setProperty(
                "DB_PASSWORD",
                Objects.requireNonNull(dotenv.get("DB_PASSWORD")));
    }
}
