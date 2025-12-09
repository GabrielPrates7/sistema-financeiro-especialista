package com.sistemaespecialista.sistemaespecialista.service;

import com.sistemaespecialista.sistemaespecialista.entities.UserEntity;
import com.sistemaespecialista.sistemaespecialista.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RespostaSistemaService respostaSistemaService;

    public UserService(UserRepository userRepository, RespostaSistemaService respostaSistemaService) {
        this.userRepository = userRepository;
        this.respostaSistemaService = respostaSistemaService;
    }

    public UserEntity salvarUsuario(UserEntity user) {
        try {
            UserEntity salvo = userRepository.save(user);

            respostaSistemaService.gerarRespostaSistema(salvo.getId());

            return salvo;

        } catch (Exception e) {
            System.out.println("Erro ao salvar o usuario: " + e.getMessage());
            throw e;
        }
    }
}