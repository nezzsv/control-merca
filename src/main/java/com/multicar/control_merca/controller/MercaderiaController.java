package com.multicar.control_merca.controller;

import com.multicar.control_merca.model.Mercaderia;
import com.multicar.control_merca.repository.MercaderiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MercaderiaController {

    @Autowired
    private MercaderiaRepository repository;

    // 1. Mostrar la lista y el formulario
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("lista", repository.findAll());
        model.addAttribute("mercaderia", new Mercaderia());
        return "index";
    }

    // 2. Guardar el producto
    @PostMapping("/guardar")
    public String guardar(Mercaderia mercaderia) {
        repository.save(mercaderia);
        return "redirect:/";
    }
}