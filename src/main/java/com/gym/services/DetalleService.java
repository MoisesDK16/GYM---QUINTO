package com.gym.services;


import com.gym.models.Detalle;
import com.gym.models.Factura;
import com.gym.models.Producto;
import com.gym.repositories.DetalleRepository;
import com.gym.repositories.FacturaRepository;
import com.gym.repositories.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
public class DetalleService {

    private final ProductoRepository productoRepository;
    private final FacturaRepository facturaRepository;
    private final DetalleRepository detalleRepository;

    public DetalleService(ProductoRepository productoRepository, FacturaRepository facturaRepository, DetalleRepository detalleRepository) {
        this.productoRepository = productoRepository;
        this.facturaRepository = facturaRepository;
        this.detalleRepository = detalleRepository;
    }

    public Detalle guardarDetalleProducto(Detalle detalle) {

        Producto producto = null;
        Factura factura = null;

        if (detalle.getProducto() != null && detalle.getProducto().getId_producto() != null) {
            producto = productoRepository.findById(detalle.getProducto().getId_producto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        }

        if (detalle.getFactura() != null && detalle.getFactura().getIdFactura() != null) {
            factura = facturaRepository.findById(detalle.getFactura().getIdFactura())
                    .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada"));
        } else {
            throw new IllegalArgumentException("ID de Factura no puede ser nulo");
        }

        if(detalle.getPrecio() == null) throw new IllegalArgumentException("Precio no encontrado");
        if(detalle.getPrecio().doubleValue() <= 0) throw new IllegalArgumentException("Precio no válido");
        if(detalle.getCantidad() == null) throw new IllegalArgumentException("Cantidad no encontrada");
        if(detalle.getTotal() == null) throw new IllegalArgumentException("Total no encontrado");

        Detalle detalleBuilder = Detalle.builder()
                .producto(producto)
                .factura(factura)
                .precio(detalle.getPrecio())
                .cantidad(detalle.getCantidad())
                .total(detalle.getTotal())
                .build();

        return detalleRepository.save(detalleBuilder);
    }

}
