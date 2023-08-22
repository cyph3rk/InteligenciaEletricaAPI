package com.InteligenciaEletricaAPI.facade;

import com.InteligenciaEletricaAPI.controller.form.EnderecoForm;
import com.InteligenciaEletricaAPI.controller.form.PessoaForm;
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
import java.util.Optional;
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

    public List<EnderecoForm> buscarPorRua(String rua) {
        List<Endereco> listaEnderecos = repositorioEnderecos.findByRua(rua);

        return listaEnderecos.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<EnderecoForm> buscarPorId(Long id) {

        try {
            Endereco endereco = repositorioEnderecos.getReferenceById(id);

            EnderecoForm enderecoForm = new EnderecoForm();
            enderecoForm.setRua(endereco.getRua());
            enderecoForm.setNumero(endereco.getNumero());
            enderecoForm.setBairro(endereco.getBairro());
            enderecoForm.setCidade(endereco.getCidade());
            enderecoForm.setEstado(endereco.getEstado());

            return Optional.of(enderecoForm);
        } catch (EntityNotFoundException ex) {
            logger.info("PessoaFacade - buscarPorId Id: " + id + (" Não cadastrado"));
            return Optional.empty();
        }
    }

    public void remove(Long id) {
        //Todo: Implementar a verificação se cadastro existe antes de deletar
        repositorioEnderecos.deleteById(id);
    }

    //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
    public void altera(Long id, EnderecoForm enderecoForm_New) {
        Endereco enderecoDB = repositorioEnderecos.getReferenceById(id);
        enderecoDB.setRua(enderecoForm_New.getRua());
        enderecoDB.setNumero(enderecoForm_New.getNumero());
        enderecoDB.setBairro(enderecoForm_New.getBairro());
        enderecoDB.setCidade(enderecoForm_New.getCidade());
        enderecoDB.setEstado(enderecoForm_New.getEstado());

        repositorioEnderecos.save(enderecoDB);
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

    public List<EnderecoForm> getAll() {
        return repositorioEnderecos
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

}













