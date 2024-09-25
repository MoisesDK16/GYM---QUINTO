package com.gym.services;

import com.gym.dto.Register;
import com.gym.models.Usuario;
import com.gym.repositories.CargoRepository;
import com.gym.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Transactional
@Validated
public class UserService {
    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CargoRepository cargoRepository;
    private final UsuarioRepository usuarioRepository;

    public UserService(UsuarioRepository userRepository, PasswordEncoder passwordEncoder, CargoRepository cargoRepository, UsuarioRepository usuarioRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cargoRepository = cargoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void register(@Validated Register data) {
        if (userRepository.existsByUsername(data.getUsername())) {
            throw new IllegalArgumentException("Email already exists");
        }

        var rol = cargoRepository.findByCargo("ADMIN").orElseThrow( () -> new IllegalArgumentException("Cargo not found"));
        var user = Usuario.builder()
                .username(data.getUsername())
                .password(passwordEncoder.encode(data.getPassword()))
                .nombre(data.getName())
                .apellido(data.getLastName())
                .roles(List.of(rol))
                .build();
        userRepository.save(user);
    }


    public void registerCliente(@Validated Register data) {
        if (userRepository.existsByUsername(data.getUsername())) {
            throw new IllegalArgumentException("Email already exists");
        }

        var rol = cargoRepository.findByCargo("CLIENTE").orElseThrow( () -> new IllegalArgumentException("Cargo not found"));
        var user = Usuario.builder()
                .username(data.getUsername())
                .password(passwordEncoder.encode(data.getPassword()))
                .nombre(data.getName())
                .apellido(data.getLastName())
                .roles(List.of(rol))
                .build();
        userRepository.save(user);
    }

    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }
}
