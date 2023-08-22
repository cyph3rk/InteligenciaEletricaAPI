package com.InteligenciaEletricaAPI.controller;


import com.InteligenciaEletricaAPI.controller.form.EnderecoForm;
import com.InteligenciaEletricaAPI.controller.form.PessoaForm;
import com.InteligenciaEletricaAPI.facade.EnderecoFacade;
import com.InteligenciaEletricaAPI.repositorio.RepositorioEnderecos;
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
@RequestMapping("/endereco")
public class EnderecoController {

    private static final Logger logger = LoggerFactory.getLogger(EnderecoController.class);

    private final RepositorioEnderecos repositorioEnderecos;

    private final Validator validator;

    private final EnderecoFacade enderecoFacade;

    @Autowired
    public EnderecoController(RepositorioEnderecos repositorioEnderecos, Validator validator, EnderecoFacade enderecoFacade) {
        this.repositorioEnderecos = repositorioEnderecos;
        this.validator = validator;
        this.enderecoFacade = enderecoFacade;
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping
    public ResponseEntity<Object>  cadastraEndereco(@RequestBody EnderecoForm enderecoForm) {
        logger.info("POST - Try : Cadastro de um novo Endereco: Rua: " + enderecoForm.getRua());

        Map<Path, String> violacoesToMap = validar(enderecoForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Long resp = enderecoFacade.salvar(enderecoForm);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Endereco JÁ cadastrado.\"}");
        }

        logger.info("POST - Sucesso : Cadastro Endereco: Rua: " + enderecoForm.getRua() + "Id: " + resp);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Endereco CADASTRADO com sucesso.\", " +
                                                              "\"id\": \"" + resp +"\"}");
    }

    @GetMapping
    public ResponseEntity<String> getAllEnderecos() {
        logger.info("GET - Pedido de todos os Enderecos cadastrados");

        String json = "Erro Inesperado";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(enderecoFacade.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(String.format(json));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEnderecoPorId(@PathVariable Long id) {
        logger.info("GET - Pedido de Endereco por Id: " + id);

        Optional<EnderecoForm> enderecoForm = enderecoFacade.buscarPorId(id);

        boolean existeRegistro = enderecoForm.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Endereco NÃO cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(enderecoForm);
    }

    //TODO: Melhor A a questão de busca por RUA. Ter só uma rua é estranho
    @GetMapping("/rua/{rua}")
    public ResponseEntity<Object> getEnderecoPorRua(@PathVariable String rua) {
        List<EnderecoForm> enderecoForm = enderecoFacade.buscarPorRua(rua);

        if (enderecoForm.size() == 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Endereco NÃO cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(enderecoForm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEnderecoPorId(@PathVariable Long id) {

        Optional<EnderecoForm> enderecoForm = enderecoFacade.buscarPorId(id);

        boolean existeRegistro = enderecoForm.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Endereço NÃO cadastrado.\"}");
        }

        enderecoFacade.remove(id);
        return ResponseEntity.ok("{\"Mensagem\": \"Endereço DELETADO com sucesso.\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alteraEnderecoPorId(@PathVariable Long id, @RequestBody EnderecoForm enderecoForm) {
        Map<Path, String> violacoesToMap = validar(enderecoForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Optional<EnderecoForm> enderecoForm_old = enderecoFacade.buscarPorId(id);

        boolean existeRegistro = enderecoForm_old.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Endereco NÃO cadastrado.\"}");
        }

        enderecoFacade.altera(id, enderecoForm);
        return ResponseEntity.status(HttpStatus.OK).body(enderecoForm);
    }


}
