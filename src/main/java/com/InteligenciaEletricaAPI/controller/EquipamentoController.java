package com.InteligenciaEletricaAPI.controller;

import com.InteligenciaEletricaAPI.controller.form.EquipamentoForm;
import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.dominio.Equipamento;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import com.InteligenciaEletricaAPI.repositorio.RepositorioEquipamentos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/equipamento")
public class EquipamentoController {

    @Autowired
    private RepositorioEquipamentos repositorioEquipamentos;

    @Autowired
    private Validator validator;

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        Map<Path, String> violacoesToMap = violacoes.stream().collect(Collectors.toMap(
                violacao -> violacao.getPropertyPath(), violacao -> violacao.getMessage()));

        return violacoesToMap;
    }

    @PostMapping
    public ResponseEntity cadastraEquipamento(@RequestBody EquipamentoForm EquipamentoForm) {
        Map<Path, String> violacoesToMap = validar(EquipamentoForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Equipamento equipamento = EquipamentoForm.toEquipamento();
        repositorioEquipamentos.salvar(equipamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipamento);
    }

    @GetMapping
    public ResponseEntity<String> getAllEquipamentos() {

        String json = "Erro";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(repositorioEquipamentos.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(String.format(json));
    }

}
