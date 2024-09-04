package com.gym.services;
import com.gym.dto.CorreoRequest;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;


    public void enviarCorreo(CorreoRequest correoRequest, MultipartFile file) {
        try {
            if (correoRequest.getDestinatario() == null || correoRequest.getDestinatario().isEmpty()) {
                throw new RuntimeException("El campo 'destinatario' no puede ser nulo o vacío.");
            }

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(correoRequest.getDestinatario());
            helper.setSubject(correoRequest.getAsunto());

            Context context = new Context();
            context.setVariable("mensaje", correoRequest.getMensaje());
            String contenidoHtml = templateEngine.process("email", context);

            helper.setText(contenidoHtml, true);

            if (file != null && !file.isEmpty()) {
                helper.addAttachment(file.getOriginalFilename(), file);
            }

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
        }
    }

}
