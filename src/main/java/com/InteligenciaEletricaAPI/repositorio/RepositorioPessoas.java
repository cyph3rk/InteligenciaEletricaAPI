package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Pessoa;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class RepositorioPessoas {

    private static int count;

    private Set<Pessoa> pessoas;

    public RepositorioPessoas() {
        count = 0;
        pessoas = new HashSet<>();
    }

    private int getId() {
        count++;
        return count;
    }

    public void salvar(Pessoa pessoa) {
        pessoa.setId(Integer.toString(getId()));
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
