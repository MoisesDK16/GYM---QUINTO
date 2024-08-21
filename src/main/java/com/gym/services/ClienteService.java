package com.gym.services;

import com.gym.models.Cliente;
import com.gym.repositories.ClienteRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public Cliente registrar(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(String id_cliente, Cliente cliente) {
        Cliente clienteActualizar = clienteRepository.findById(id_cliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id_cliente));

        clienteActualizar.setTipo_identificacion(cliente.getTipo_identificacion());
        clienteActualizar.setNombre(cliente.getNombre());
        clienteActualizar.setPrimer_apellido(cliente.getPrimer_apellido());
        clienteActualizar.setSegundo_apellido(cliente.getSegundo_apellido());
        clienteActualizar.setCorreo(cliente.getCorreo());
        clienteActualizar.setClave(cliente.getClave());
        clienteActualizar.setDireccion(cliente.getDireccion());
        clienteActualizar.setTelefono(cliente.getTelefono());
        return clienteRepository.save(clienteActualizar);
    }

    public void eliminarCliente(String id_cliente){
        clienteRepository.deleteById(id_cliente);
    }

}
