package com.InteligenciaEletricaAPI.controller.form;

import com.InteligenciaEletricaAPI.dominio.Equipamento;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import com.InteligenciaEletricaAPI.repositorio.RepositorioEquipamentos;
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

}
