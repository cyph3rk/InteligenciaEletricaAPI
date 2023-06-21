package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Pessoa;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class RepositorioPessoas {

    private Set<Pessoa> pessoas;

    public RepositorioPessoas() {
        pessoas = new HashSet<>();
    }

    public void salvar(Pessoa pessoa) {
        pessoas.add(pessoa);
    }

    public Optional<Pessoa> buscar(String nome, String dataNascimento, String sexo, String parentesco) {
        return pessoas.stream()
                .filter(pessoa -> pessoa.identificadaPor(nome, dataNascimento, sexo, parentesco))
                .findFirst();
    }

    public void remove(Pessoa pessoa) {
        pessoas.remove(pessoa);
    }

}
