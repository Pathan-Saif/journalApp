package net.engineeringsaif.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthCheker {
    @GetMapping("/health-check")
    public String healthCheck(){
        return "ok";
    }
}
