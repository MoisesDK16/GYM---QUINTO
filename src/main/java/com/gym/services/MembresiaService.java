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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Page<Membresia> listar(int page, int size) {
        return membresiaRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Membresia> uno(int id) {
        return Optional.ofNullable(membresiaRepository.findById(id)
                .orElseThrow((() -> new IllegalArgumentException("Membresia no encontrada"))));
    }

    public List<Membresia> listarPorCliente(String id_cliente) {
        return membresiaRepository.findMembresiasByClienteId_cliente(id_cliente);
    }

    public List<Membresia> listarPorClientePrimer_apellido(String primer_apellido) {
        return membresiaRepository.findByClientePrimer_apellido(primer_apellido);
    }

    public List<Membresia> listarPorPlanNombre(String nombre) {
        return membresiaRepository.findMembresiaByPlanNombre(nombre);
    }

    public List<Membresia> listarPorDias_restantes() {
        return membresiaRepository.findMembresiasByOrderByDias_restantesAsc();
    }

    public List<Membresia> listarPorEstado(String estado) {
        return membresiaRepository.findMembresiasByEstado(estado);
    }

    public Membresia renovarMembresia(Date fechaInicio, Date fechaFin, Integer idMembresia) {
        Membresia membresia = membresiaRepository.findById(idMembresia)
                .orElseThrow(() -> new IllegalArgumentException("Membresia no encontrada"));

        Plan planAsoc = planRepository.findById(membresia.getPlan().getId_plan())
                .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado"));

        membresia.setFechaInicio(fechaInicio);
        membresia.setFechaFin(fechaFin);
        membresia.setDias_restantes(membresia.getDias_restantes() + planAsoc.getDuracion_dias());
        membresia.setEstado("ACTIVO");

        return membresiaRepository.save(membresia);
    }

    public List<Membresia> desactivarMembresias() {

        List<Membresia> desactivados = new ArrayList<>();

        for (Membresia membresia : membresiaRepository.findAll()) {
            if (membresia.getDias_restantes() == 0) {
                membresia.setEstado("INACTIVO");
                desactivados.add(membresiaRepository.save(membresia));
            }
        }

        return desactivados;
    }

}
