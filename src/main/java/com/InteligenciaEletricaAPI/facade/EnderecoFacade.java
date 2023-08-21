package com.InteligenciaEletricaAPI.facade;

import com.InteligenciaEletricaAPI.controller.form.EnderecoForm;
import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import com.InteligenciaEletricaAPI.repositorio.RepositorioEnderecos;
import com.InteligenciaEletricaAPI.repositorio.RepositorioPessoas;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EnderecoFacade {

    private static final Logger logger = LoggerFactory.getLogger(EnderecoFacade.class);

    private final RepositorioPessoas repositorioPessoas;
    private final RepositorioEnderecos repositorioEnderecos;

    @Autowired
    public EnderecoFacade(RepositorioPessoas repositorioPessoas, RepositorioEnderecos repositorioEnderecos) {
        this.repositorioPessoas = repositorioPessoas;
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

    public Long salvarIdsEnderecos(Long idPessoa, Set<Long> idsEnderecos) {
        Pessoa pessoa = new Pessoa();
        try {
            pessoa = repositorioPessoas.getReferenceById(idPessoa);

            Set<Endereco> enderecos = new HashSet<>();
            for (Long idEndereco : idsEnderecos) {
                Endereco endereco = repositorioEnderecos.getById(idEndereco);
                enderecos.add(endereco);
            }

            pessoa.setEnderecos(enderecos);
            repositorioPessoas.save(pessoa);

            return pessoa.getId();

        } catch (EntityNotFoundException ex) {
            logger.info("PessoaFacade - salvarIdsEnderecos Id: " + idPessoa + (" Não cadastrado"));
            return -1L;
        }
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
