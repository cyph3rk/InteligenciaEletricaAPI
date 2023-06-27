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

    public Integer salvar(Pessoa pessoa) {
        boolean encontrado = pessoas.stream().anyMatch(pes -> pes.getNome().equals(pessoa.getNome()));
        if (encontrado) {
            return -1;
        }

        pessoa.setId(Integer.toString(getId()));
        pessoas.add(pessoa);
        return Integer.parseInt(pessoa.getId());
    }

    public Optional<Pessoa> buscarPorId(String id) {
        return pessoas.stream()
                .filter(pessoa -> pessoa.identificadaPorId(id))
                .findFirst();
    }

    public Optional<Pessoa> buscarPorNome(String nome) {
        return pessoas.stream()
                .filter(pessoa -> pessoa.identificadaPorNome(nome))
                .findFirst();
    }

    public Object getAll() {
        return pessoas;
    }

    public void remove(Pessoa pessoa) {
        pessoas.remove(pessoa);
    }

    //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
    public void altera(Pessoa pessoaOld, Pessoa pessoaNew) {
        remove(pessoaOld);
        pessoaNew.setId(pessoaOld.getId());
        pessoas.add(pessoaNew);
    }
}
