package com.gym.controllers;



import com.gym.models.Factura;
import com.gym.services.files.ListarFacturas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FileController{

    @Autowired
    private ListarFacturas fileService;

    public FileController(ListarFacturas fileService) {
        this.fileService = fileService;
    }

    @GetMapping (value = "/all", produces = "application/pdf")
    public ModelAndView getAllAsPdf() {
        List<Factura> listaFacturas = fileService.ListarFacturas();
        return new ModelAndView(fileService, "facturas", listaFacturas);
    }
}
