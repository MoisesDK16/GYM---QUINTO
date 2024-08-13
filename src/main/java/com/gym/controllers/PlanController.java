package com.gym.controllers;

import com.gym.models.Plan;
import com.gym.services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<Plan> buscarPlanId(@PathVariable int id) {
        Optional<Plan> plan = planService.buscarPlanId(id);
        return plan.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Plan> crearPlan(@RequestBody Plan plan) {
        Plan nuevoPlan = planService.registrar(plan);
        return new ResponseEntity<>(nuevoPlan, HttpStatus.CREATED);
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
