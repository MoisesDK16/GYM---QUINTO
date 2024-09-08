package com.gym.services;

import com.gym.models.Cliente;
import com.gym.models.Membresia;
import com.gym.models.Plan;
import com.gym.repositories.ClienteRepository;
import com.gym.repositories.MembresiaRepository;
import com.gym.repositories.PlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class MembresiaService {

    private final MembresiaRepository membresiaRepository;
    private final ClienteRepository clienteRepository;
    private final PlanRepository planRepository;

    public Membresia registrar(Membresia membresia) {

        if (membresia == null) throw new IllegalArgumentException("Membresia no puede ser nulo");
        if (membresia.getCliente() == null) throw new IllegalArgumentException("id_Cliente no puede ser nulo");

        Cliente cliente = clienteRepository.findById(membresia.getCliente().getId_cliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        if (membresia.getPlan() == null) throw new IllegalArgumentException("id_Plan no puede ser nulo");

        Plan plan = planRepository.findById(membresia.getPlan().getId_plan())
                .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado"));

        Membresia nuevaMembresia = Membresia.builder()
                .cliente(cliente)
                .plan(plan)
                .fechaInicio(membresia.getFechaInicio())
                .fechaFin(membresia.getFechaFin())
                .estado(membresia.getEstado())
                .build();

        return membresiaRepository.save(nuevaMembresia);
    }


}
