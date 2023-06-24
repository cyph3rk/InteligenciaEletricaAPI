package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Equipamento;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class RepositorioEquipamentos {

    private static int count;

    private Set<Equipamento> equipamentos;

    public RepositorioEquipamentos() {
        count = 0;
        equipamentos = new HashSet<>();
    }

    private int getId() {
        count++;
        return count;
    }

    public boolean salvar(Equipamento equipamento) {

        boolean encontrado = equipamentos.stream().anyMatch(nome -> nome.getNome().equals(equipamento.getNome()));
        if (encontrado) {
            return false;
        }

        equipamento.setId(Integer.toString(getId()));
        equipamentos.add(equipamento);
        return true;
    }

    public Optional<Equipamento> buscar(String nome, String modelo, String potencia) {
        return equipamentos.stream()
                .filter(endereco -> endereco.identificadaPor(nome, modelo, potencia))
                .findFirst();
    }

    public Optional<Equipamento> buscarPorId(String id) {
        return equipamentos.stream()
                .filter(endereco -> endereco.identificadaPorId(id))
                .findFirst();
    }

    public Optional<Equipamento> buscarPorNome(String nome) {
        return equipamentos.stream()
                .filter(endereco -> endereco.identificadaPorNome(nome))
                .findFirst();
    }

    public Object getAll() {
        return equipamentos;
    }

    public void remove(Equipamento equipamento) {
        equipamentos.remove(equipamento);
    }

    public void altera(Equipamento equipamentoOld, Equipamento equipamentoNew) {
        remove(equipamentoOld);
        equipamentoNew.setId(equipamentoOld.getId());
        equipamentos.add(equipamentoNew);
    }
}
