package com.InteligenciaEletricaAPI.facade;

import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import com.InteligenciaEletricaAPI.dto.EnderecoDTO;
import com.InteligenciaEletricaAPI.dto.PessoaDTO;
import com.InteligenciaEletricaAPI.repositorio.RepositorioEnderecos;
import com.InteligenciaEletricaAPI.repositorio.RepositorioPessoas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoFacade {

    @Autowired
    private RepositorioEnderecos repository;

    public Long salvar(EnderecoDTO enderecoDTO) {
        List<EnderecoDTO> encontrado = buscarPorRua(enderecoDTO.getRua());
        if (encontrado.size() > 1) {
            return -1L;
        }

        Endereco endereco = new Endereco();
        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());

        repository.save(endereco);
        enderecoDTO.setId(endereco.getId());

        return enderecoDTO.getId();
    }

    private EnderecoDTO converter (Endereco endereco) {
        EnderecoDTO result = new EnderecoDTO();
        result.setId(endereco.getId());
        result.setRua(endereco.getRua());
        result.setNumero(endereco.getNumero());
        result.setBairro(endereco.getBairro());
        result.setCidade(endereco.getCidade());
        result.setEstado(endereco.getEstado());

        return result;
    }

    public List<EnderecoDTO> buscarPorRua(String rua) {
        List<Endereco> listaEnderecos = repository.findByRua(rua);

        return listaEnderecos.stream()
                .map(this::converter).collect(Collectors.toList());
    }

}
