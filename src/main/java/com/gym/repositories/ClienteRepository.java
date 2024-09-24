package com.gym.repositories;

import com.gym.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {


    @Query("SELECT c FROM Cliente c WHERE c.correo LIKE %:correo%")
    Optional<Cliente> findByCorreo(String correo);

}
