package com.gym.services;


import com.gym.models.Cliente;
import com.gym.models.Detalle;
import com.gym.models.Factura;
import com.gym.repositories.ClienteRepository;
import com.gym.repositories.DetalleRepository;
import com.gym.repositories.FacturaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Log4j2
@AllArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepository;
    private final ClienteRepository clienteRepository;
    private final DetalleRepository detalleRepository;


    public Factura generarFactura(
            Cliente cliente,
            String ruc,
            LocalDateTime fechaEmision,
            String metodoPago,
            BigDecimal subtotal,
            BigDecimal iva,
            BigDecimal total) {

        if (cliente.getId_cliente() != null && clienteRepository.existsById(cliente.getId_cliente())) {
            cliente = clienteRepository.findById(cliente.getId_cliente())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        }

        if(ruc == null) throw new IllegalArgumentException("RUC no encontrado");
        if(fechaEmision == null) throw new IllegalArgumentException("fechaEmision no encontrado");
        if(metodoPago == null) throw new IllegalArgumentException("Metodo de pago nulo o vacio");
        if(subtotal == null) throw new IllegalArgumentException("Subtotal no encontrado");
        if(iva == null) throw new IllegalArgumentException("Iva no encontrado");
        if(total == null) throw new IllegalArgumentException("Total no encontrado");

        Factura factura = Factura.builder()
                .cliente(cliente)
                .ruc(ruc)
                .fechaEmision(fechaEmision)
                .metodoPago(metodoPago)
                .subtotal(subtotal)
                .iva(iva)
                .total(total)
                .build();

        return facturaRepository.save(factura);
    }

    public List<Factura> listarFacturas(){
        return facturaRepository.findAll();
    }

    public Optional<Factura> findLastFactura(String idCliente) {
        return facturaRepository.findLastFactura(idCliente);
    }

    /*Filtros*/


    //Productos

    public List<Factura> findFacturasWithProductos() {
        return facturaRepository.findFacturasWithProductos();
    }

    public List<Factura> findFacturasProductosByFechaEmision(String fechaInicio, String fechaFin, String metodoPago) {
        if(metodoPago.equals("null")){
            return facturaRepository.findFacturasProductosByFecha(fechaInicio, fechaFin);
        }else{
            return facturaRepository.findFacturasProductosByFecha(fechaInicio, fechaFin, metodoPago);
        }
    }

    public List<Factura> findFacturasProductosByCliente(String idCliente) {
        return facturaRepository.findFacturasProductosByCliente(idCliente);
    }

    public List<Factura> findFacturasByNombreCompleto(String nombreApellido) {
        return facturaRepository.findFacturasByNombreCompleto(nombreApellido);
    }


    //Membresias

    public List<Detalle> findFacturasWithMembresia() {
        return detalleRepository.findFacturasWithMembresia();
    }

    public List<Detalle> findFacturasMembresiasByFechaEmision(String fechaInicio, String fechaFin, String metodoPago) {
        if(metodoPago.equals("null")){
            return detalleRepository.findFacturasMembresiasByFecha(fechaInicio, fechaFin);
        }else{
            return detalleRepository.findFacturasMembresiasByFecha(fechaInicio, fechaFin, metodoPago);
        }
    }

    public List<Detalle> findFacturasMembresiasByCliente(String idCliente) {
        return detalleRepository.findFacturasMembresiasByCliente(idCliente);
    }

    public List<Detalle> findFacturasByNombreCompletoM(String nombreApellido) {
        return detalleRepository.findFacturasByNombreCompleto(nombreApellido);
    }

}
