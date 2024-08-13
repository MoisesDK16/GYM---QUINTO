package com.gym.repositories;

import com.gym.models.Personal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Integer> {

    Personal findByIdentificacion(String Identificacion);

    Personal deleteByIdentificacion(String Identificacion);
}
