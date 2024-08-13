package com.gym.services;


import com.gym.models.Cliente;
import com.gym.models.Factura;
import com.gym.models.Personal;
import com.gym.repositories.ClienteRepository;
import com.gym.repositories.FacturaRepository;
import com.gym.repositories.PersonalRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import static io.micrometer.common.util.StringUtils.isEmpty;


@Service
@Transactional
@Log4j2
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final ClienteRepository clienteRepository;
    private final PersonalRepository personalRepository;

    public FacturaService(FacturaRepository facturaRepository, ClienteRepository clienteRepository, PersonalRepository personalRepository) {
        this.facturaRepository = facturaRepository;
        this.clienteRepository = clienteRepository;
        this.personalRepository = personalRepository;
    }

    public void generarFacturaProducto(
            Personal user,
            Cliente cliente,
            String ruc,
            Date fechaEmision,
            String metodoPago,
            BigDecimal subtotal,
            BigDecimal iva,
            BigDecimal total) {

        if (cliente.getId_cliente() != null && clienteRepository.existsById(cliente.getId_cliente())) {
            cliente = clienteRepository.findById(cliente.getId_cliente())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        }

        if (user.getId_usuario() != 0 && personalRepository.existsById(user.getId_usuario())) {
            user = personalRepository.findById(user.getId_usuario())
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        }

        if(ruc == null) throw new IllegalArgumentException("RUC no encontrado");
        if(fechaEmision == null) throw new IllegalArgumentException("fechaEmision no encontrado");
        if(metodoPago == null) throw new IllegalArgumentException("Metodo de pago nulo o vacio");
        if(subtotal == null) throw new IllegalArgumentException("Subtotal no encontrado");
        if(iva == null) throw new IllegalArgumentException("Iva no encontrado");
        if(total == null) throw new IllegalArgumentException("Total no encontrado");

        Factura factura = Factura.builder()
                .usuario(user)
                .cliente(cliente)
                .ruc(ruc)
                .fechaEmision((java.sql.Date) fechaEmision)
                .metodoPago(metodoPago)
                .subtotal(subtotal)
                .iva(iva)
                .total(total)
                .build();

        facturaRepository.save(factura);
    }

}
