package com.InteligenciaEletricaAPI.controller;

import com.InteligenciaEletricaAPI.controller.form.PessoaForm;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import com.InteligenciaEletricaAPI.repositorio.RepositorioPessoas;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private static final Logger logger = LoggerFactory.getLogger(PessoaController.class);

    private final RepositorioPessoas repositorioPessoas;

    private final Validator validator;

    @Autowired
    public PessoaController(RepositorioPessoas repositorioPessoas, Validator validator) {
        this.repositorioPessoas = repositorioPessoas;
        this.validator = validator;
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping
    public ResponseEntity<Object>  cadastraPessoa(@RequestBody PessoaForm pessoaForm) {
        logger.info("POST - Try : Cadastro de uma nova Pessoa: Nome: " + pessoaForm.getNome());

        Map<Path, String> violacoesToMap = validar(pessoaForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Pessoa pessoa = pessoaForm.toPessoa();
        Integer resp = repositorioPessoas.salvar(pessoa);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Pessoa JÁ cadastrado.\"}");
        }

        logger.info("POST - Sucesso : Cadastro Pessoa: Nome: " + pessoaForm.getNome() + "Id: " + resp);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Pessoa CADASTRADO com sucesso.\", " +
                                                              "\"id\": \"" + resp +"\"}");
    }

    @GetMapping
    public ResponseEntity<String> getAllPessoas() {
        logger.info("GET - Pedido de todos as Pessoas cadastradas");

        String json = "Erro Inesperado";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(repositorioPessoas.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(String.format(json));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPessoaPorId(@PathVariable Integer id) {
        logger.info("GET - Pedido de Pessoa por Id: " + id);

        Optional<Pessoa> pessoa = repositorioPessoas.buscarPorId(Integer.toString(id));

        boolean existeRegistro = pessoa.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Pessoa NÃO cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(pessoa);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> getPessoaPorNome(@PathVariable String nome) {
        Optional<Pessoa> pessoa = repositorioPessoas.buscarPorNome(nome);

        boolean existeRegistro = pessoa.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Pessoa NÃO cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(pessoa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePessoaPorId(@PathVariable Integer id) {

        Optional<Pessoa> pessoa = repositorioPessoas.buscarPorId(Integer.toString(id));

        boolean existeRegistro = pessoa.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Pessoa NÃO cadastrado.\"}");
        }

        repositorioPessoas.remove(pessoa.get());
        return ResponseEntity.ok("{\"Mensagem\": \"Pessoa DELETADO com sucesso.\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alteraPessoaPorId(@PathVariable Integer id, @RequestBody PessoaForm pessoaForm) {
        Map<Path, String> violacoesToMap = validar(pessoaForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Pessoa pessoaNew = pessoaForm.toPessoa();

        Optional<Pessoa> pessoaOld = repositorioPessoas.buscarPorId(Integer.toString(id));
        boolean existeRegistro = pessoaOld.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Pessoa NÃO cadastrado.\"}");
        }

        repositorioPessoas.altera(pessoaOld.get(), pessoaNew);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaNew);
    }

}










