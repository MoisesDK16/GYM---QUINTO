package com.gym.controllers;

import com.gym.models.Cliente;
import com.gym.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    private final ClienteService clienteService;

    @Autowired
    public ClientController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Cliente>> obtenerClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Cliente> clients = clienteService.obtenerClientes(page, size);

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/{id_cliente}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable String id_cliente) {
        Optional<Cliente> cliente = clienteService.obtenerCliente(id_cliente);

        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/crear")
    public ResponseEntity<Cliente> crearCliente(
            @RequestParam String idCliente,
            @RequestParam String tipoIdentificacion,
            @RequestParam String nombre,
            @RequestParam String primerApellido,
            @RequestParam(required = false) String segundoApellido,
            @RequestParam(required = false) String correo,
            @RequestParam(required = false) String clave,
            @RequestParam(required = false) String direccion,
            @RequestParam(required = false) String telefono
    ) {
        Cliente cliente = clienteService.crearCliente(idCliente, tipoIdentificacion, nombre, primerApellido, segundoApellido, correo, clave, direccion, telefono);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @PutMapping("/modificar/{idCliente}")
    public ResponseEntity<Void> modificarCliente(
            @PathVariable String idCliente,
            @RequestParam String tipoIdentificacion,
            @RequestParam String nombre,
            @RequestParam String primerApellido,
            @RequestParam(required = false) String segundoApellido,
            @RequestParam(required = false) String correo,
            @RequestParam(required = false) String clave,
            @RequestParam(required = false) String direccion,
            @RequestParam(required = false) String telefono
    ) {
        clienteService.modificarCliente(idCliente, tipoIdentificacion, nombre, primerApellido, segundoApellido, correo, clave, direccion, telefono);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{idCliente}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable String idCliente) {
        clienteService.eliminarCliente(idCliente);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
