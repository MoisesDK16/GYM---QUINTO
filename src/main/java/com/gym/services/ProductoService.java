package com.gym.services;

import com.gym.models.Categoria;
import com.gym.models.Producto;
import com.gym.repositories.CategoriaRepository;
import com.gym.repositories.ProductoRepository;
import com.gym.services.files.UploadFileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UploadFileService uploadFileService;

    public Page<Producto> listar(int page, int size) {
        Page<Producto> productos = productoRepository.findAll(PageRequest.of(page, size));
        return productos;
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws MalformedURLException {
        Resource file = uploadFileService.load(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }


    public Producto obtenerProducto(String id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto con id " + id + " no encontrado"));
    }

    public Producto registrar(String idProducto, Categoria categoria, String nombre, int stock, double precioCompra,
                              double margenGanancia, double precioVenta, String fecha_caducacion, String descripcion,
                              MultipartFile image) throws IOException {

        if (categoria == null || categoria.getId_categoria() == 0) {
            throw new IllegalArgumentException("La categoría no puede ser nula");
        }

        String idImg = uploadFileService.copy(image);

        String imageUrl = "http://localhost:8080/api/productos/uploads/" + idImg;
        Producto producto = null;
            producto = Producto.builder()
                    .idProducto(idProducto)
                    .categoria(categoria)
                    .nombre(nombre)
                    .stock(stock)
                    .precioCompra(BigDecimal.valueOf(precioCompra))
                    .margenGanancia(BigDecimal.valueOf(margenGanancia))
                    .precioVenta(BigDecimal.valueOf(precioVenta))
                    .fecha_caducacion(LocalDate.parse(fecha_caducacion))
                    .descripcion(descripcion)
                    .imagen(imageUrl)
                    .build();
        return productoRepository.save(producto);
    }


    public Producto actualizar(String idProducto, String nombre ,  Integer categoriaId, int stock, double precioCompra,
                               double margenGanancia, double precioVenta, String fechaCaducacion,
                               String descripcion, MultipartFile image) throws IOException {

        if (idProducto == null || idProducto.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del producto no debe ser nulo o vacío");
        }

        Producto productoExistente = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto con id " + idProducto + " no encontrado"));

        if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría con id " + categoriaId + " no encontrada"));
            productoExistente.setCategoria(categoria);
        }
        productoExistente.setNombre(nombre);
        productoExistente.setStock(stock);
        productoExistente.setPrecioCompra(BigDecimal.valueOf(precioCompra));
        productoExistente.setMargenGanancia(BigDecimal.valueOf(margenGanancia));
        productoExistente.setPrecioVenta(BigDecimal.valueOf(precioVenta));
        productoExistente.setFecha_caducacion(LocalDate.parse(fechaCaducacion));
        productoExistente.setDescripcion(descripcion);

        String idImg = uploadFileService.copy(image);

        String imageUrl = "http://localhost:8080/api/productos/uploads/" + idImg;

        productoExistente.setImagen(imageUrl);

        return productoRepository.save(productoExistente);
    }


    public void actualizarStock(String id_producto, int cantidad) {
        Producto producto = productoRepository.findById(id_producto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        producto.setStock(producto.getStock()-cantidad);
        productoRepository.save(producto);
    }


    public void eliminar(String id){
        productoRepository.deleteById(id);
    }

    //Excepciones Propias
    public static class ProductoNotFoundException extends RuntimeException {
        public ProductoNotFoundException(String message) {
            super(message);
        }
    }
}
