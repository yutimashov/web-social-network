package com.getjavajob.uiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Service for serving all the static data (css, js scripts, images etc.).
 * Deployed as war app.
 */
@SpringBootApplication
public class UiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UiServiceApplication.class, args);
    }

}
