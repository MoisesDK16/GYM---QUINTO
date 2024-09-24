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
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Cliente>> obtenerClientes(@RequestParam int page, @RequestParam int size) {
        Page<Cliente> clients = clienteService.listarClientes(page, size);

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("me/{id_cliente}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable String id_cliente) {
        Optional<Cliente> cliente = clienteService.obtenerCliente(id_cliente);

        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("me/correo/{correo}")
    public ResponseEntity<Cliente> obtenerClientePorCorreo(@PathVariable String correo) {
        Optional<Cliente> cliente = clienteService.findByCorreo(correo);

        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        clienteService.registrar(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @PutMapping("me/actualizar/{idCliente}")
    public ResponseEntity<Cliente> modificarCliente(@PathVariable String idCliente,@RequestBody Cliente cliente) {
        clienteService.actualizar(idCliente, cliente);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{idCliente}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable String idCliente) {
        clienteService.eliminarCliente(idCliente);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
