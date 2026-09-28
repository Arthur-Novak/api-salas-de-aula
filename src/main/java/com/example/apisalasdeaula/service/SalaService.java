package com.example.apisalasdeaula.service;
import com.example.apisalasdeaula.model.sala.Sala;
import com.example.apisalasdeaula.model.sala.Situacao;
import com.example.apisalasdeaula.repository.SalaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public Sala cadastrar(Sala sala) {
        if (salaRepository.existsByCodigo(sala.getCodigo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Código da sala já cadastrado"
            );
        }

        sala.setId(null);
        return salaRepository.save(sala);
    }

    public List<Sala> buscarTodas() {
        return salaRepository.findAll();
    }

    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sala não encontrada"
                ));
    }

    public Sala atualizar(Long id, Sala dados) {
        Sala sala = buscarPorId(id);

        if (!sala.getCodigo().equals(dados.getCodigo())
                && salaRepository.existsByCodigo(dados.getCodigo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Código da sala já cadastrado"
            );
        }

        sala.setNome(dados.getNome());
        sala.setCodigo(dados.getCodigo());
        sala.setCapacidadeAlunos(dados.getCapacidadeAlunos());
        sala.setQuantidadeComputadores(dados.getQuantidadeComputadores());
        sala.setAnoConstrucao(dados.getAnoConstrucao());
        sala.setArea(dados.getArea());
        sala.setSituacao(dados.getSituacao());

        return salaRepository.save(sala);
    }

    public void excluir(Long id) {
        Sala sala = buscarPorId(id);

        if (sala.getSituacao() == Situacao.DISPONIVEL) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Salas disponíveis não podem ser excluídas"
            );
        }

        salaRepository.delete(sala);
    }
}
