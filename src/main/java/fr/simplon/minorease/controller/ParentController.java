package fr.simplon.minorease.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class ParentController {
    @GetMapping("/")
    public String afficherPageAccueil() {
        return "index";
    }
}
