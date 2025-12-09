package com.sistemaespecialista.sistemaespecialista.service;

import com.sistemaespecialista.sistemaespecialista.entities.RespostaSistemaEntity;
import com.sistemaespecialista.sistemaespecialista.entities.UserEntity;
import com.sistemaespecialista.sistemaespecialista.repository.RespostaSistemaRepository;
import com.sistemaespecialista.sistemaespecialista.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RespostaSistemaService {

    private final RespostaSistemaRepository respostaSistemaRepository;
    private final UserRepository userRepository;

    public RespostaSistemaService(RespostaSistemaRepository respostaSistemaRepository, UserRepository userRepository) {
        this.respostaSistemaRepository = respostaSistemaRepository;
        this.userRepository = userRepository;
    }

    public RespostaSistemaEntity gerarRespostaSistema(Long usuarioId) {

        Optional<UserEntity> optionalUser = userRepository.findById(usuarioId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        UserEntity user = optionalUser.get();

        float salario = user.getSalario();
        float gastoFixo = user.getGastoFixo();
        float reservaAtual = user.getReserva();
        float dividaAtual = user.getDivida();
        String objetivoFinanceiro = user.getObjetivo();

        float valorDisponivel = salario - gastoFixo;
        if (valorDisponivel <= 0) {
            throw new RuntimeException("O usuário não possui saldo disponível após os gastos fixos.");
        }

        float divida;
        float reserva;
        float investimento;
        float objetivo;

        switch (user.getPerfil().toLowerCase()) {
            case "conservador":
                divida = 0.40f;
                reserva = 0.30f;
                investimento = 0.20f;
                objetivo = 0.10f;
                break;
            case "moderado":
                divida = 0.30f;
                reserva = 0.25f;
                investimento = 0.30f;
                objetivo = 0.15f;
                break;
            case "arrojado":
                divida = 0.20f;
                reserva = 0.20f;
                investimento = 0.45f;
                objetivo = 0.15f;
                break;
            default:
                throw new RuntimeException("Perfil inválido: " + user.getPerfil());
        }

        if (objetivoFinanceiro == null
                || objetivoFinanceiro.trim().isEmpty()
                || objetivoFinanceiro.equalsIgnoreCase("nada")
                || objetivoFinanceiro.equalsIgnoreCase("nenhum")) {

            float redist = objetivo;
            objetivo = 0f;
            float total = divida + reserva + investimento;
            if (total > 0) {
                divida += (divida / total) * redist;
                reserva += (reserva / total) * redist;
                investimento += (investimento / total) * redist;
            }
        }

        float reservaNecessaria = gastoFixo * 6;
        if (reservaAtual >= reservaNecessaria) {
            float redist = reserva;
            reserva = 0f;
            float total = divida + investimento + objetivo;
            if (total > 0) {
                divida += (divida / total) * redist;
                investimento += (investimento / total) * redist;
                objetivo += (objetivo / total) * redist;
            }
        }

        float valorQuitacaoDividas;
        float valorReservaEmergencia;
        float valorInvestimentos;
        float valorObjetivo;

        if (dividaAtual > 0) {

            if (valorDisponivel >= dividaAtual) {
                valorQuitacaoDividas = dividaAtual;
                float restante = valorDisponivel - dividaAtual;
                float total = reserva + investimento + objetivo;

                if (total > 0) {
                    valorReservaEmergencia = restante * (reserva / total);
                    valorInvestimentos = restante * (investimento / total);
                    valorObjetivo = restante * (objetivo / total);
                } else {
                    valorReservaEmergencia = 0;
                    valorInvestimentos = 0;
                    valorObjetivo = 0;
                }

            } else {
                valorQuitacaoDividas = valorDisponivel;
                valorReservaEmergencia = 0;
                valorInvestimentos = 0;
                valorObjetivo = 0;
            }

        } else {

            valorQuitacaoDividas = 0;
            valorReservaEmergencia = valorDisponivel * reserva;
            valorInvestimentos = valorDisponivel * investimento;
            valorObjetivo = valorDisponivel * objetivo;
        }

        RespostaSistemaEntity respostaSistema = new RespostaSistemaEntity();
        respostaSistema.setUsuario(user);
        respostaSistema.setValorGastosFixos(gastoFixo);
        respostaSistema.setValorQuitacaoDividas(roundTwoDecimals(valorQuitacaoDividas));
        respostaSistema.setValorReservaEmergencia(roundTwoDecimals(valorReservaEmergencia));
        respostaSistema.setValorInvestimentos(roundTwoDecimals(valorInvestimentos));
        respostaSistema.setValorObjetivo(roundTwoDecimals(valorObjetivo));

        return respostaSistemaRepository.save(respostaSistema);
    }

    public RespostaSistemaEntity buscarPorId(Long id) {
        try {
            return respostaSistemaRepository.findByUsuarioId(id)
                    .orElseThrow(() -> new RuntimeException("Resposta não encontrada"));
        } catch (Exception e) {
            System.out.println("Erro ao buscar resposta: " + e.getMessage());
            throw e;
        }
    }

    private float roundTwoDecimals(float value) {
        return Math.round(value * 100.0f) / 100.0f;
    }
}
