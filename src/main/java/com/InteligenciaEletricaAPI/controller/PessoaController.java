package com.InteligenciaEletricaAPI.controller;

import com.InteligenciaEletricaAPI.controller.form.PessoaForm;
import com.InteligenciaEletricaAPI.facade.EnderecoFacade;
import com.InteligenciaEletricaAPI.facade.PessoaFacade;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private static final Logger logger = LoggerFactory.getLogger(PessoaController.class);

    private final Validator validator;

    private final PessoaFacade pessoaFacade;
    private final EnderecoFacade enderecoFacade;

    @Autowired
    public PessoaController(Validator validator, PessoaFacade pessoaFacade, EnderecoFacade enderecoFacade) {
        this.validator = validator;
        this.pessoaFacade = pessoaFacade;
        this.enderecoFacade = enderecoFacade;
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping
    public ResponseEntity<Object> cadastraPessoa(@RequestBody PessoaForm pessoaForm) {

        //Todo: Implementar regra de não existir mais de uma pessoa como cliente por
        // Relacionamento em um ou mais endereços

        logger.info("POST - Try : Cadastro de uma nova Pessoa: Nome: " + pessoaForm.getNome());

        Map<Path, String> violacoesToMap = validar(pessoaForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Long resp = pessoaFacade.salvar(pessoaForm);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Pessoa JÁ cadastrado.\"}");
        }

        logger.info("POST - Sucesso : Cadastro Pessoa: Nome: " + pessoaForm.getNome() + "Id: " + resp);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Pessoa CADASTRADO com sucesso.\", " +
                                                              "\"id\": \"" + resp +"\"}");
    }

    @PostMapping("/{id}/cadastraEnderecos")
    public ResponseEntity<Object> cadastraEnderecosPessoa(@PathVariable Long id,
                                                @RequestBody Set<Long> idsEnderecos) {
        enderecoFacade.salvarIdsEnderecos(id, idsEnderecos);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<String> getAllPessoas() {
        logger.info("GET - Pedido de todos as Pessoas cadastradas");

        String json = "Erro Inesperado";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(pessoaFacade.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(String.format(json));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPessoaPorId(@PathVariable Long id) {
        logger.info("GET - Pedido de Pessoa por Id: " + id);

        Optional<PessoaForm> pessoaForm = pessoaFacade.buscarPorId(id);

        boolean existeRegistro = pessoaForm.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Pessoa NÃO cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(pessoaForm);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> getPessoaPorNome(@PathVariable String nome) {
        List<PessoaForm> pessoaForms = pessoaFacade.buscarPorNome(nome);

        if (pessoaForms.size() == 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Pessoa NÃO cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(pessoaForms);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePessoaPorId(@PathVariable Long id) {

        Optional<PessoaForm> pessoaForm = pessoaFacade.buscarPorId(id);

        boolean existeRegistro = pessoaForm.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Pessoa NÃO cadastrado.\"}");
        }

        pessoaFacade.remove(id);
        return ResponseEntity.ok("{\"Mensagem\": \"Pessoa DELETADO com sucesso.\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alteraPessoaPorId(@PathVariable Long id, @RequestBody PessoaForm pessoaForm) {
        Map<Path, String> violacoesToMap = validar(pessoaForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Optional<PessoaForm> pessoaForm_old = pessoaFacade.buscarPorId(id);

        boolean existeRegistro = pessoaForm_old.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Pessoa NÃO cadastrado.\"}");
        }

        pessoaFacade.altera(id, pessoaForm);
        return ResponseEntity.status(HttpStatus.OK).body(pessoaForm);
    }

}










