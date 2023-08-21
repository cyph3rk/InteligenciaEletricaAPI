package com.InteligenciaEletricaAPI.facade;

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
public class PessoaFacade {

    private static final Logger logger = LoggerFactory.getLogger(PessoaFacade.class);

    private final RepositorioPessoas repositorioPessoas;
    private final RepositorioEnderecos repositorioEnderecos;

    @Autowired
    public PessoaFacade(RepositorioPessoas repositorioPessoas, RepositorioEnderecos repositorioEnderecos) {
        this.repositorioPessoas = repositorioPessoas;
        this.repositorioEnderecos = repositorioEnderecos;
    }

    public Long salvar(PessoaForm pessoaForm) {
        List<PessoaForm> encontrado = buscarPorNome(pessoaForm.getNome());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaForm.getNome());
        pessoa.setData_nascimento(pessoaForm.getData_nascimento());
        pessoa.setSexo(pessoaForm.getSexo());
        pessoa.setCodigo_cliente(pessoaForm.getCodigo_cliente());
        pessoa.setRelacionamento(pessoaForm.getRelacionamento());

        repositorioPessoas.save(pessoa);

        return pessoa.getId();
    }

    public List<PessoaForm> buscarPorNome(String nome) {
        List<Pessoa> listaPessoas = repositorioPessoas.findByNome(nome);

        return listaPessoas.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<PessoaForm> buscarPorId(Long id) {

        try {
            Pessoa pessoa = repositorioPessoas.getReferenceById(id);

            PessoaForm pessoaForm = new PessoaForm();
            pessoaForm.setNome(pessoa.getNome());
            pessoaForm.setData_nascimento(pessoa.getData_nascimento());
            pessoaForm.setSexo(pessoa.getSexo());
            pessoaForm.setCodigo_cliente(pessoa.getCodigo_cliente());
            pessoaForm.setRelacionamento(pessoa.getRelacionamento());

            return Optional.of(pessoaForm);
        } catch (EntityNotFoundException ex) {
            logger.info("PessoaFacade - buscarPorId Id: " + id + (" Não cadastrado"));
            return Optional.empty();
        }
    }

    public void remove(Long id) {
        repositorioPessoas.deleteById(id);
//        return "DELETED";
    }

    //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
    public void altera(Long id, PessoaForm pessoaForm_New) {
        Pessoa pessoaDB = repositorioPessoas.getReferenceById(id);
        pessoaDB.setNome(pessoaForm_New.getNome());
        pessoaDB.setData_nascimento(pessoaForm_New.getData_nascimento());
        pessoaDB.setSexo(pessoaForm_New.getSexo());
        pessoaDB.setCodigo_cliente(pessoaForm_New.getCodigo_cliente());
        pessoaDB.setRelacionamento(pessoaForm_New.getRelacionamento());

        repositorioPessoas.save(pessoaDB);
    }

    private PessoaForm converter (Pessoa pessoa) {
        PessoaForm result = new PessoaForm();
        result.setNome(pessoa.getNome());
        result.setData_nascimento(pessoa.getData_nascimento());
        result.setSexo(pessoa.getSexo());
        result.setCodigo_cliente(pessoa.getCodigo_cliente());
        result.setRelacionamento(pessoa.getRelacionamento());

        return result;
    }

    public List<PessoaForm> getAll() {
        return repositorioPessoas
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

}
