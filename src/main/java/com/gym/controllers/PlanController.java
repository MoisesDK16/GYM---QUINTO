package com.gym.controllers;

import com.gym.dto.NewPlan;
import com.gym.models.Plan;
import com.gym.models.Servicio;
import com.gym.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/planes")
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping("/listar")
    public ResponseEntity<Page<Plan>> listarPlanes(
            int page,
            int size) {
        Page<Plan> planes = planService.listar(page, size);
        return new ResponseEntity<>(planes, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Plan>> listarPlanes() {
        List<Plan> planes = planService.listar();
        return new ResponseEntity<>(planes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plan> buscarPlanId(@PathVariable int id) {
        Optional<Plan> plan = planService.buscarPlanId(id);
        return plan.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Plan> registrarPlan(
            @RequestParam("nombre") String nombre,
            @RequestParam("costo") double costo,
            @RequestParam("duracion_dias") int duracionDias,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("imagen") MultipartFile imagen) throws IOException {

        Plan nuevoPlan = planService.registrar(nombre, costo, duracionDias, descripcion, imagen);

        return new ResponseEntity<>(nuevoPlan, HttpStatus.CREATED);
    }


    @PostMapping("/agregar-servicio")
    public ResponseEntity<String> agregarServicio(@RequestParam Integer id_plan, @RequestParam Integer id_servicio) {
        planService.agregarServicio(id_plan, id_servicio);
        return ResponseEntity.ok("Servicio agregado correctamente");
    }

    @PostMapping("/eliminar-servicio")
    public ResponseEntity<String> eliminarServicio(@RequestParam Integer id_plan, @RequestParam Integer id_servicio) {
        planService.eliminarServicio(id_plan, id_servicio);
        return ResponseEntity.ok("Servicio eliminado correctamente");
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Plan> actualizar(@PathVariable int id, @RequestBody Plan plan) {
        Plan planActualizado = planService.actualizar(id, plan);
        return new ResponseEntity<>(planActualizado, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPlan(@PathVariable int id) {
        planService.eliminarPlan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
