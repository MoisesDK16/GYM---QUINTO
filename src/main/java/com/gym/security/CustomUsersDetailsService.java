package com.gym.security;

import com.gym.models.Cargo;
import com.gym.models.Usuario;
import com.gym.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUsersDetailsService implements UserDetailsService {
    private final UsuarioRepository userRepository;

    public Collection<GrantedAuthority> mapToAuthorities(List<Cargo> cargos) {
        return cargos.stream()
                .map(cargo -> new SimpleGrantedAuthority(cargo.getCargo())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = userRepository.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + email));
        return new User(usuario.getUsername(), usuario.getPassword(), mapToAuthorities(usuario.getRoles()));
    }
}
