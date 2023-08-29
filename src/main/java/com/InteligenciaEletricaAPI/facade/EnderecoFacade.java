package com.InteligenciaEletricaAPI.facade;

import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import com.InteligenciaEletricaAPI.dto.EnderecoDto;
import com.InteligenciaEletricaAPI.repositorio.IEnderecosRepositorio;
import com.InteligenciaEletricaAPI.repositorio.IPessoasRepositorio;
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

    private final IPessoasRepositorio repositorioPessoas;
    private final IEnderecosRepositorio repositorioEnderecos;

    @Autowired
    public EnderecoFacade(IPessoasRepositorio repositorioPessoas, IEnderecosRepositorio repositorioEnderecos) {
        this.repositorioPessoas = repositorioPessoas;
        this.repositorioEnderecos = repositorioEnderecos;
    }

    public Long salvar(EnderecoDto enderecoDto) {
        List<EnderecoDto> encontrado = buscarPorRua(enderecoDto.getRua());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        Endereco endereco = new Endereco();
        endereco.setRua(enderecoDto.getRua());
        endereco.setNumero(enderecoDto.getNumero());
        endereco.setBairro(enderecoDto.getBairro());
        endereco.setCidade(enderecoDto.getCidade());
        endereco.setEstado(enderecoDto.getEstado());

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

    public List<EnderecoDto> buscarPorRua(String rua) {
        List<Endereco> listaEnderecos = repositorioEnderecos.findByRua(rua);

        return listaEnderecos.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<EnderecoDto> buscarPorId(Long id) {

        try {
            Endereco endereco = repositorioEnderecos.getReferenceById(id);

            EnderecoDto enderecoDto = new EnderecoDto();
            enderecoDto.setId(endereco.getId());
            enderecoDto.setRua(endereco.getRua());
            enderecoDto.setNumero(endereco.getNumero());
            enderecoDto.setBairro(endereco.getBairro());
            enderecoDto.setCidade(endereco.getCidade());
            enderecoDto.setEstado(endereco.getEstado());

            return Optional.of(enderecoDto);
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
    public void altera(EnderecoDto enderecoDto_New) {
        Endereco endereco = repositorioEnderecos.getReferenceById(enderecoDto_New.getId());
        endereco.setId(enderecoDto_New.getId());
        endereco.setRua(enderecoDto_New.getRua());
        endereco.setNumero(enderecoDto_New.getNumero());
        endereco.setBairro(enderecoDto_New.getBairro());
        endereco.setCidade(enderecoDto_New.getCidade());
        endereco.setEstado(enderecoDto_New.getEstado());

        repositorioEnderecos.save(endereco);
    }

    private EnderecoDto converter (Endereco endereco) {
        EnderecoDto result = new EnderecoDto();
        result.setId(endereco.getId());
        result.setRua(endereco.getRua());
        result.setNumero(endereco.getNumero());
        result.setBairro(endereco.getBairro());
        result.setCidade(endereco.getCidade());
        result.setEstado(endereco.getEstado());

        return result;
    }

    public List<EnderecoDto> getAll() {
        return repositorioEnderecos
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

}













