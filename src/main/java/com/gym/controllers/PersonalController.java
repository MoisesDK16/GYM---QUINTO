package com.gym.controllers;

import com.gym.models.Personal;
import com.gym.services.PersonalService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/personal")
public class PersonalController{

    @Autowired
    private PersonalService personalService;

    @GetMapping()
    public ResponseEntity<Page<Personal>> listarPersonal(int page,int size) {
        Page<Personal> personal = personalService.listarPersonal(page, size);
        return new ResponseEntity<>(personal, HttpStatus.OK);
    }

    @GetMapping("/{identificacion}")
    public ResponseEntity<Personal> buscarPersonal(@PathVariable String identificacion) {
        Personal personal = personalService.buscarPersonal(identificacion);
        if (personal != null) {
            return new ResponseEntity<>(personal, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<Personal> registrarPersonal(@RequestBody Personal personal) {
        Personal nuevoPersonal = personalService.crearPersonal(personal);
        return new ResponseEntity<>(nuevoPersonal, HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{identificacion}")
    public ResponseEntity<Personal> actualizarPersonal(@PathVariable String identificacion, @RequestBody Personal personal) {
        try {
            Personal personalActualizado = personalService.actualizarPersonal(identificacion, personal);
            return new ResponseEntity<>(personalActualizado, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPersonal(@PathVariable int id) {
        try {
            personalService.eliminarPersonal(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
