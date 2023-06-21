package com.InteligenciaEletricaAPI.controller;


import com.InteligenciaEletricaAPI.controller.form.EnderecoForm;
import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.repositorio.RepositorioEnderecos;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private RepositorioEnderecos repositorioEndereco;

    @Autowired
    private Validator validator;

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        Map<Path, String> violacoesToMap = violacoes.stream().collect(Collectors.toMap(
                violacao -> violacao.getPropertyPath(), violacao -> violacao.getMessage()));

        return violacoesToMap;
    }

    @PostMapping
    public ResponseEntity cadastraEndereco(@RequestBody EnderecoForm enderecoForm) {
        Map<Path, String> violacoesToMap = validar(enderecoForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Endereco endereco = enderecoForm.toEndereco();
        repositorioEndereco.salvar(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
    }
}
