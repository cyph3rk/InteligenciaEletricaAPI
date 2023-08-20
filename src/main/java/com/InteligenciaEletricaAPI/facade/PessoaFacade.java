package com.InteligenciaEletricaAPI.facade;

import com.InteligenciaEletricaAPI.controller.PessoaController;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import com.InteligenciaEletricaAPI.dto.PessoaDTO;
import com.InteligenciaEletricaAPI.repositorio.RepositorioPessoas;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaFacade {

    private static final Logger logger = LoggerFactory.getLogger(PessoaFacade.class);

    @Autowired
    private RepositorioPessoas repository;

    public Long salvar(PessoaDTO pessoaDTO) {
        List<PessoaDTO> encontrado = buscarPorNome(pessoaDTO.getNome());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setData_nascimento(pessoaDTO.getData_nascimento());
        pessoa.setSexo(pessoaDTO.getSexo());
        pessoa.setCodigo_cliente(pessoaDTO.getCodigo_cliente());
        pessoa.setRelacionamento(pessoaDTO.getRelacionamento());

        repository.save(pessoa);
        pessoaDTO.setId(pessoa.getId());

        return pessoaDTO.getId();
    }

    public List<PessoaDTO> buscarPorNome(String nome) {
        List<Pessoa> listaPessoas = repository.findByNome(nome);

        return listaPessoas.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<PessoaDTO> buscarPorId(Long id) {

        try {
            Pessoa pessoa = repository.getReferenceById(id);

            PessoaDTO pessoaDTO = new PessoaDTO();
            pessoaDTO.setId(pessoa.getId());
            pessoaDTO.setNome(pessoa.getNome());
            pessoaDTO.setData_nascimento(pessoa.getData_nascimento());
            pessoaDTO.setSexo(pessoa.getSexo());
            pessoaDTO.setCodigo_cliente(pessoa.getCodigo_cliente());
            pessoaDTO.setRelacionamento(pessoa.getRelacionamento());

            return Optional.of(pessoaDTO);
        } catch (EntityNotFoundException ex) {
            logger.info("PessoaFacade - buscarPorId Id: " + id + (" Não cadastrado"));
            return Optional.empty();
        }
    }

    public void remove(PessoaDTO pessoaDTO) {
        repository.deleteById(pessoaDTO.getId());
//        return "DELETED";
    }

    //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
    public void altera(Long id, PessoaDTO pessoaDTO_New) {
        Pessoa pessoaDB = repository.getReferenceById(id);
        pessoaDB.setNome(pessoaDTO_New.getNome());
        pessoaDB.setData_nascimento(pessoaDTO_New.getData_nascimento());
        pessoaDB.setSexo(pessoaDTO_New.getSexo());
        pessoaDB.setCodigo_cliente(pessoaDTO_New.getCodigo_cliente());
        pessoaDB.setRelacionamento(pessoaDTO_New.getRelacionamento());

        repository.save(pessoaDB);
//        return pessoaDTO_New;
    }

    private PessoaDTO converter (Pessoa pessoa) {
        PessoaDTO result = new PessoaDTO();
        result.setId(pessoa.getId());
        result.setNome(pessoa.getNome());
        result.setData_nascimento(pessoa.getData_nascimento());
        result.setSexo(pessoa.getSexo());
        result.setCodigo_cliente(pessoa.getCodigo_cliente());
        result.setRelacionamento(pessoa.getRelacionamento());

        return result;
    }

    public List<PessoaDTO> getAll() {
        return repository
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

}
