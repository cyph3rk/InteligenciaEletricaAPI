package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Equipamento;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class RepositorioEquipamentos {

    private static int count;

    private final Set<Equipamento> equipamentos;

    public RepositorioEquipamentos() {
        count = 0;
        equipamentos = new HashSet<>();
    }

    private int getId() {
        count++;
        return count;
    }

    public Integer salvar(Equipamento equipamento) {
        boolean encontrado = equipamentos.stream().anyMatch(equip -> equip.getNome().equals(equipamento.getNome()));
        if (encontrado) {
            return -1;
        }

        equipamento.setId(Integer.toString(getId()));
        equipamentos.add(equipamento);
        return Integer.parseInt(equipamento.getId());
    }

    public Optional<Equipamento> buscarPorId(String id) {
        return equipamentos.stream()
                .filter(equipamento -> equipamento.identificadaPorId(id))
                .findFirst();
    }

    public Optional<Equipamento> buscarPorNome(String nome) {
        return equipamentos.stream()
                .filter(equipamento -> equipamento.identificadaPorNome(nome))
                .findFirst();
    }

    public Object getAll() {
        return equipamentos;
    }

    public void remove(Equipamento equipamento) {
        equipamentos.remove(equipamento);
    }

    //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
    public void altera(Equipamento equipamentoOld, Equipamento equipamentoNew) {
        remove(equipamentoOld);
        equipamentoNew.setId(equipamentoOld.getId());
        equipamentos.add(equipamentoNew);
    }
}
