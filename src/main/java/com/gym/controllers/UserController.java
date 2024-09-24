package com.gym.controllers;

import com.gym.dto.AuthResponse;
import com.gym.dto.Login;
import com.gym.dto.Register;
import com.gym.repositories.CargoRepository;
import com.gym.repositories.UsuarioRepository;
import com.gym.security.JwtTokenProvider;
import com.gym.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/users")
public class UserController {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private CargoRepository cargoRepository;
    private UsuarioRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private UserService service;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, CargoRepository cargoRepository, UsuarioRepository userRepository, JwtTokenProvider jwtTokenProvider, UserService service) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.cargoRepository = cargoRepository;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody Login data
    ) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                data.getUsername(),
                data.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody Register data
    ) {
        service.register(data);
        return ResponseEntity.ok("User created");
    }

    @PostMapping("me/registerCliente")
    public ResponseEntity<String> registerCliente(
            @RequestBody Register data
    ) {
        service.registerCliente(data);
        return ResponseEntity.ok("User created");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }


}
