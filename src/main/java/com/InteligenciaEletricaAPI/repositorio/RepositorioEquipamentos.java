package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.dominio.Equipamento;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class RepositorioEquipamentos {

    private Set<Equipamento> equipamentos;

    public RepositorioEquipamentos() {
        equipamentos = new HashSet<>();
    }

    public void salvar(Equipamento equipamento) {
        equipamentos.add(equipamento);
    }

    public Optional<Equipamento> buscar(String nome, String modelo, String potencia) {
        return equipamentos.stream()
                .filter(endereco -> endereco.identificadaPor(nome, modelo, potencia))
                .findFirst();
    }

    public void remove(Equipamento equipamento) {
        equipamentos.remove(equipamento);
    }
}
