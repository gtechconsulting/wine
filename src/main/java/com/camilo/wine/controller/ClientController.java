package com.camilo.wine.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/client")
public class ClientController {

    @GetMapping(value = "/clientes-fieis")
    public ResponseEntity<String> getShopping() {
        return new ResponseEntity<>("Lista de clientes fieis", HttpStatus.OK);
    }

    @GetMapping(value = "/recomendacao/cliente/tipo")
    public ResponseEntity<String> getRecomendation() {
        return new ResponseEntity<>("Recomendacao de vinho", HttpStatus.OK);
    }
}
