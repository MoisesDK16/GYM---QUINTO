package com.gym.controllers;

import com.gym.dto.CorreoRequest;
import com.gym.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/apiEmail")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/enviar-correo")
    public ResponseEntity<String> enviarCorreo(@RequestPart("campos") @Validated CorreoRequest correoRequest,
                                               @RequestPart("archivo") MultipartFile file) {
        try {
            emailService.enviarCorreo(correoRequest, file);
            return new ResponseEntity<>("Correo enviado exitosamente.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al enviar el correo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}