package com.gym.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class EmailConfig {

    @Value("${email.username}")
    private String email;

    @Value("${email.password}")
    private String password;

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Ejemplo: smtp.gmail.com
        properties.put("mail.smtp.port", "587"); // Ejemplo: 587

        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // Confianza en todos los certificados (útil para Gmail o servicios similares)

        // Si el certificado no es confiable, puedes deshabilitar la validación de certificado (usar con cuidado)
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Usar TLS v1.2
        properties.put("mail.smtp.starttls.required", "true"); // TLS debe ser requerido
        properties.put("mail.smtp.ssl.checkserveridentity", "true"); // Verificar identidad del servidor

        // Propiedades para depurar problemas de conexión (opcional)
        properties.put("mail.debug", "true"); // Activa la depuración de JavaMail (verás logs en consola)

        return properties;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(getMailProperties());
        mailSender.setUsername(email);
        mailSender.setPassword(password);
        return mailSender;
    }

    @Bean
    public ResourceLoader resourceLoader() {
        return new DefaultResourceLoader();
    }
}
