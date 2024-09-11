package com.gym.services;

import com.gym.dto.NewPlan;
import com.gym.models.Plan;
import com.gym.models.Servicio;
import com.gym.repositories.PlanRepository;
import com.gym.repositories.ServicioRepository;
import com.gym.services.files.UploadFileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final ServicioRepository servicioRepository;
    private final UploadFileService uploadFileService;

    public Page<Plan> listar(int page, int size){
        return planRepository.findAll(PageRequest.of(page, size));
    }

    public List<Plan> listar(){
        return planRepository.findAll();
    }

    public Optional<Plan> buscarPlanId(int id){
        return planRepository.findById(id);
    }


    public Plan registrar(
            String nombre,
            double costo,
            int duracionDias,
            String descripcion,
            MultipartFile imagen) {

        try {
            String img = uploadFileService.copy(imagen);
            img = "http://localhost:8080/api/productos/uploads/" + img;

            var plan = Plan.builder()
                    .nombre(nombre)
                    .costo(BigDecimal.valueOf(costo))
                    .duracion_dias(duracionDias)
                    .descripcion(descripcion)
                    .imagen(img)
                    .build();

            return planRepository.save(plan);

        } catch (Exception e) {
            log.error("Error al registrar plan", e);
            throw new RuntimeException("Error al registrar plan");
        }
    }


    public void agregarServicio(int id_plan, int id_servicio) {
        Plan planExistente = planRepository.findById(id_plan)
                .orElseThrow(() -> new IllegalArgumentException("ID de plan inválido"));

        Servicio servicioExistente = servicioRepository.findById(id_servicio)
                .orElseThrow(() -> new IllegalArgumentException("ID de servicio inválido"));

        planExistente.getServicios().add(servicioExistente);

        BigDecimal nuevoCosto = planExistente.getCosto().add(servicioExistente.getPrecio());
        planExistente.setCosto(nuevoCosto);

        planRepository.save(planExistente);

        log.info("Servicio: " + servicioExistente + " agregado al Plan: " + planExistente);
    }

    public void eliminarServicio(int id_plan, int id_servicio) {
        Plan planExistente = planRepository.findById(id_plan)
                .orElseThrow(() -> new IllegalArgumentException("ID de plan inválido"));

        Servicio servicioExistente = servicioRepository.findById(id_servicio)
                .orElseThrow(() -> new IllegalArgumentException("ID de servicio inválido"));

        planExistente.getServicios().remove(servicioExistente);

        BigDecimal nuevoCosto = planExistente.getCosto().subtract(servicioExistente.getPrecio());
        planExistente.setCosto(nuevoCosto);

        planRepository.save(planExistente);

        log.info("Servicio: " + servicioExistente + " eliminado del Plan: " + planExistente);
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
