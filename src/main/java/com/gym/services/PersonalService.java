package com.gym.services;

import com.gym.models.Personal;
import com.gym.repositories.PersonalRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Log4j2
public class PersonalService {

    private final PersonalRepository personalRepository;

    public PersonalService(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    public Page<Personal> listarPersonal(int page, int size) {
        return personalRepository.findAll(PageRequest.of(page, size));
    }

    public Personal buscarPersonal(String identificacion) {
        return personalRepository.findByIdentificacion(identificacion);
    }

    public Personal crearPersonal(Personal personal) {
        if (personal.getCargo() == null || personal.getCargo().getId_cargo() == 0) {
            throw new IllegalArgumentException("El cargo no puede ser nulo y debe tener un ID válido");
        }
        return personalRepository.save(personal);
    }

    public Personal actualizarPersonal(String identificacion, Personal personal) {

        if(identificacion == null || personal == null) throw new NullPointerException();

        var person = personalRepository.findByIdentificacion(identificacion);

        if (person == null) {
            throw new EntityNotFoundException("Persona con identificación " + identificacion + " no encontrada");
        }

        person.setCargo(personal.getCargo());
        person.setIdentificacion(personal.getIdentificacion());
        person.setTipoIdentificacion(String.valueOf(personal.getTipoIdentificacion()));
        person.setCorreo(personal.getCorreo());
        person.setClave(personal.getClave());
        person.setDireccion(personal.getDireccion());
        person.setFechaNacimiento(personal.getFechaNacimiento());

        return personalRepository.save(person);

    }

    public void eliminarPersonal(int id) {
        personalRepository.deleteById(id);
    }

}
