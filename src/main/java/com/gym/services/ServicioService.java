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

import java.util.NoSuchElementException;

@Log4j2
@Service
@Transactional
@AllArgsConstructor
@Validated
public class ServicioService {
    private final ServicioRepository repository;
    private final CategoriaRepository categoriaRepository;

    public Categoria obtenerCategoria(String nombre){
        return categoriaRepository.findByCategoria(nombre).orElseThrow();
    }

    public Servicio obtenerServicio(Integer id){
        return repository.findById(id).orElseThrow();
    }

    public Servicio crearServicio(Servicio servicio) {
        try {
            var builder = Servicio.builder()
                    .nombre(servicio.getNombre())
                    .categoria(servicio.getCategoria())
                    .precio(servicio.getPrecio());

            var servicioCreado = builder.build();
            return repository.save(servicioCreado);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Error al crear el servicio: " + e.getMessage());
        }
    }


    public void actualizarServicio(Servicio servicio) {
        var servicioEncontrado = obtenerServicio(servicio.getId_servicio());
        if (servicio.getNombre() != null) servicioEncontrado.setNombre(servicio.getNombre());
        if (servicio.getCategoria() != null) servicioEncontrado.setCategoria(servicio.getCategoria());
        if (servicio.getPrecio() != null) servicioEncontrado.setPrecio(servicio.getPrecio());
        repository.save(servicioEncontrado);
    }

    public Page<Servicio> ServiciosPorCategoria(Categoria categoria, int page, int size){
        return repository.findAllByCategoria(categoria, PageRequest.of(page, size));
    }

    public Page<Servicio> listarServicios(int page, int size){
        return repository.findAll(PageRequest.of(page, size));
    }

    public void eliminarServicio(Integer id_servicio){
        repository.deleteById(id_servicio);
    }

}
