package com.barney.echo.controllers;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/echo")
public class EchoController {

    private final Environment environment;

    public EchoController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping()
    public String echo() {
        return "Working on port=" + environment.getProperty("local.server.port");
    }
}
