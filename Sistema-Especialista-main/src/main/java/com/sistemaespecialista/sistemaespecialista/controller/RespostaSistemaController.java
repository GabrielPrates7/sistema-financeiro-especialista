package com.sistemaespecialista.sistemaespecialista.controller;

import com.sistemaespecialista.sistemaespecialista.entities.RespostaSistemaEntity;
import com.sistemaespecialista.sistemaespecialista.service.RespostaSistemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respostas-sistema")
@CrossOrigin(origins = "*")
public class RespostaSistemaController {

    private final RespostaSistemaService respostaSistemaService;

    public RespostaSistemaController(RespostaSistemaService respostaSistemaService) {
        this.respostaSistemaService = respostaSistemaService;
    }

    @PostMapping("/gerar/{usuarioId}")
    public ResponseEntity<?> gerarRespostaSistema(@PathVariable Long usuarioId) {
        try {
            RespostaSistemaEntity resposta = respostaSistemaService.gerarRespostaSistema(usuarioId);

            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Long usuarioId) {
        try {
            RespostaSistemaEntity resposta = respostaSistemaService.buscarPorId(usuarioId);
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Resposta não encontrada");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            RespostaSistemaEntity resposta = respostaSistemaService.buscarPorId(id);
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Resposta não encontrada");
        }
    }
}
