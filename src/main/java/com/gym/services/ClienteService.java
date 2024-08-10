package com.gym.services;

import com.gym.models.Cliente;
import com.gym.repositories.ClienteRepository;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Log4j2
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Page<Cliente> listarClientes(int page, int size){
        return clienteRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Cliente> obtenerCliente(String id_cliente){
        return clienteRepository.findById(id_cliente);
    }

    public Cliente crearCliente(
            @NotNull String idCliente,
            @NotNull String tipoIdentificacion,
            @NotNull String nombre,
            @NotNull String primerApellido,
            String segundoApellido,
            String correo,
            String clave,
            String direccion,
            String telefono){

        var cliente = Cliente.builder()
                .id_cliente(idCliente)
                .tipo_identificacion(tipoIdentificacion)
                .nombre(nombre)
                .primer_apellido(primerApellido)
                .segundo_apellido(segundoApellido)
                .correo(correo)
                .clave(clave)
                .direccion(direccion)
                .telefono(telefono)
                .build();

        return clienteRepository.save(cliente);
    }


    public Cliente modificarCliente(
            @NotNull String idCliente,
            @NotNull String tipoIdentificacion,
            @NotNull String nombre,
            @NotNull String primerApellido,
            String segundoApellido,
            String correo,
            String clave,
            String direccion,
            String telefono
    ) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + idCliente));

        cliente.setTipo_identificacion(tipoIdentificacion);
        cliente.setNombre(nombre);
        cliente.setPrimer_apellido(primerApellido);
        cliente.setSegundo_apellido(segundoApellido);
        cliente.setCorreo(correo);
        cliente.setClave(clave);
        cliente.setDireccion(direccion);
        cliente.setTelefono(telefono);

        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(String id_cliente){
        clienteRepository.deleteById(id_cliente);
    }

}
