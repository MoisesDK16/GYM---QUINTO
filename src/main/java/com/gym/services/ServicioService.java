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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Optional<Servicio> obtenerServicio(Integer id){
        return Optional.ofNullable(repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado")));
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

    public void actualizarServicio(Integer id_servicio, Servicio servicio) {
        Optional<Servicio> servicioEncontrado = Optional.ofNullable(obtenerServicio(id_servicio).
                orElseThrow(() -> new RuntimeException("Servicio no encontrado")));
        if (servicioEncontrado.isPresent()) {
            Servicio s = servicioEncontrado.get();
            if (servicio.getNombre() != null) s.setNombre(servicio.getNombre());
            if (servicio.getCategoria() != null) s.setCategoria(servicio.getCategoria());
            if (servicio.getPrecio() != null) s.setPrecio(servicio.getPrecio());
            repository.save(s);
        }
    }

    public List<Servicio> ServiciosPorCategoria(Categoria categoria) {
        return repository.findAllByCategoria(categoria);
    }

    public List<Servicio> listarServicios(){
        return repository.findAll();
    }

    public void eliminarServicio(Integer id_servicio){
        repository.deleteById(id_servicio);
    }

}
