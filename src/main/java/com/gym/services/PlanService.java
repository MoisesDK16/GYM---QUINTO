package com.gym.services;

import com.gym.models.Plan;
import com.gym.repositories.PlanRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Transactional
@Log4j2
public class PlanService {

    private PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Page<Plan> listar(int page, int size){
        return planRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Plan> buscarPlanId(int id){
        return planRepository.findById(id);
    }

    public Plan registrar(Plan plan) {

        if(isEmpty(plan.getCosto()) || plan.getCosto().compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Costo invalido");
        if(isEmpty(plan.getDuracion_dias()) || plan.getDuracion_dias()<0 || plan.getDuracion_dias() > 365) throw new IllegalArgumentException("Duracion invalido");

        Plan nuevoPlan = Plan.builder()
                .nombre(plan.getNombre())
                .descripcion(plan.getDescripcion())
                .costo(plan.getCosto())
                .duracion_dias(plan.getDuracion_dias())
                .build();
        return planRepository.save(nuevoPlan);
    }

    public Plan actualizar(int id_plan, Plan plan) {

        Optional<Plan> planOptional = Optional.of(planRepository.findById(id_plan)
                .orElseThrow(()-> new IllegalArgumentException("id invalido")));

        Plan planExistente = planOptional.get();

        planExistente.setNombre(plan.getNombre());
        if (plan.getCosto().compareTo(BigDecimal.ZERO) > 0) planExistente.setCosto(plan.getCosto());
        if (plan.getDuracion_dias() > 15) planExistente.setDuracion_dias(plan.getDuracion_dias());
        planExistente.setDescripcion(plan.getDescripcion());
        return planRepository.save(planExistente);
    }

    public void eliminarPlan(int id_plan){
        planRepository.deleteById(id_plan);
    }
}
