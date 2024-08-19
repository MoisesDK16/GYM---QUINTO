package com.gym.services;

import com.gym.models.Categoria;
import com.gym.models.Servicio;
import com.gym.repositories.CategoriaRepository;
import com.gym.repositories.ServicioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
@Log4j2
@Service
@Transactional
@AllArgsConstructor
@Validated
public class ServicioService {
    private final ServicioRepository repository;
    private final CategoriaRepository categoriaRepository;

    public Categoria obtenerCategoria(String nombre){
        return categoriaRepository.findByNombre(nombre).orElseThrow();
    }


    public Servicio obtenerServicio(Long id){
        return repository.findById(id).orElseThrow();
    }
    public void crearServicio(
            String nombre,
            String categoria,
            BigDecimal precio){

        var cat = obtenerCategoria(categoria);

        var builder = Servicio.builder()
                .nombre(nombre)
                .categoria(cat)
                .precio(precio);
        var servicio = builder.build();
         repository.save(servicio);
    }

    public void actualizarServicio(
            Long id,
            String nombre,
            String categoria,
            BigDecimal precio) {
        var servicio = obtenerServicio(id);
        if (nombre != null) servicio.setNombre(nombre);
        if (categoria != null) servicio.setCategoria(obtenerCategoria(categoria));
        if (precio != null) servicio.setPrecio(precio);
        repository.save(servicio);

    }

    public Page<Servicio> ServiciosPorCategoria(Categoria categoria, int page, int size){
        return repository.findAllByCategoria(categoria, PageRequest.of(page, size));
    }

    public Page<Servicio> listarServicios(int page, int size){
        return repository.findAll(PageRequest.of(page, size));
    }

    public void eliminarServicio(Long id){
        repository.deleteById(id);
    }




}
