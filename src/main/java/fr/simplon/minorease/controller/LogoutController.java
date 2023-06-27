package fr.simplon.minorease.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @PostMapping("logout")
    public void logout() {}
}
