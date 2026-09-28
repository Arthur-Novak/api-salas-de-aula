package com.example.apisalasdeaula.controller;

import com.example.apisalasdeaula.model.sala.Sala;
import com.example.apisalasdeaula.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping
    public ResponseEntity<Sala> cadastrar(@Valid @RequestBody Sala sala) {
        Sala cadastrada = salaService.cadastrar(sala);

        return ResponseEntity
                .created(URI.create("/salas/" + cadastrada.getId()))
                .body(cadastrada);
    }

    @GetMapping
    public ResponseEntity<List<Sala>> buscarTodas() {
        return ResponseEntity.ok(salaService.buscarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(salaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sala> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Sala dados
    ) {
        return ResponseEntity.ok(salaService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        salaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
