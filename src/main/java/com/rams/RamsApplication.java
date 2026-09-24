package com.rams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class RamsApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(RamsApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printServerUrl() {
        String port = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🚀 RAMS Application is running!");
        System.out.println("=".repeat(80));
        System.out.println("📍 Local URL:     http://localhost:" + port + contextPath);
        System.out.println("📍 Network URL:   http://127.0.0.1:" + port + contextPath);
        System.out.println("=".repeat(80));
        System.out.println("🔐 Login Credentials:");
        System.out.println("   Username: bro");
        System.out.println("   Password: bro");
        System.out.println("=".repeat(80) + "\n");
    }
}
