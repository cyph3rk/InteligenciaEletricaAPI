package com.InteligenciaEletricaAPI.facade;

import com.InteligenciaEletricaAPI.controller.form.EnderecoForm;
import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.repositorio.RepositorioEnderecos;
import com.InteligenciaEletricaAPI.repositorio.RepositorioPessoas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoFacade {

    private RepositorioEnderecos repositorioEnderecos;

    @Autowired
    public EnderecoFacade(RepositorioEnderecos repositorioEnderecos) {
        this.repositorioEnderecos = repositorioEnderecos;
    }

    public Long salvar(EnderecoForm enderecoForm) {
        List<EnderecoForm> encontrado = buscarPorRua(enderecoForm.getRua());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        Endereco endereco = new Endereco();
        endereco.setRua(enderecoForm.getRua());
        endereco.setNumero(enderecoForm.getNumero());
        endereco.setBairro(enderecoForm.getBairro());
        endereco.setCidade(enderecoForm.getCidade());
        endereco.setEstado(enderecoForm.getEstado());

        repositorioEnderecos.save(endereco);

        return endereco.getId();
    }

    private EnderecoForm converter (Endereco endereco) {
        EnderecoForm result = new EnderecoForm();
        result.setRua(endereco.getRua());
        result.setNumero(endereco.getNumero());
        result.setBairro(endereco.getBairro());
        result.setCidade(endereco.getCidade());
        result.setEstado(endereco.getEstado());

        return result;
    }

    public List<EnderecoForm> buscarPorRua(String rua) {
        List<Endereco> listaEnderecos = repositorioEnderecos.findByRua(rua);

        return listaEnderecos.stream()
                .map(this::converter).collect(Collectors.toList());
    }

}
