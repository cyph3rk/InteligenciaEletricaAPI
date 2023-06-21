package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Endereco;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class RepositorioEnderecos {

    private Set<Endereco> enderecos;

    public RepositorioEnderecos() {
        enderecos = new HashSet<>();
    }

    public void salvar(Endereco endereco) {
        enderecos.add(endereco);
    }

    public Optional<Endereco> buscar(String rua, String numero, String bairro, String cidade, String estado) {
        return enderecos.stream()
                .filter(endereco -> endereco.identificadaPor(rua, numero, bairro, cidade, estado))
                .findFirst();
    }

    public void remove(Endereco endereco) {
        enderecos.remove(endereco);
    }

}
