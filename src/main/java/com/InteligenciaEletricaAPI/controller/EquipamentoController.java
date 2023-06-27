package com.InteligenciaEletricaAPI.controller;

import com.InteligenciaEletricaAPI.controller.form.EquipamentoForm;
import com.InteligenciaEletricaAPI.dominio.Equipamento;
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

import java.util.Map;
import java.util.Optional;
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

        return violacoes.stream().collect(Collectors.toMap(
                        ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping
    public ResponseEntity<Object> cadastraEquipamento(@RequestBody EquipamentoForm equipamentoForm) {
        Map<Path, String> violacoesToMap = validar(equipamentoForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Equipamento equipamento = equipamentoForm.toEquipamento();
        Integer resp = repositorioEquipamentos.salvar(equipamento);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Equipamento JÁ cadastrado.\"}");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Equipamento CADASTRADO com sucesso.\", " +
                                                              "\"id\": \"" + resp +"\"}");
    }

    @GetMapping
    public ResponseEntity<String> getAllEquipamentos() {

        String json = "Erro Inesperado";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(repositorioEquipamentos.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(String.format(json));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEquipamentoPorId(@PathVariable Integer id) {
        Optional<Equipamento> equipamento = repositorioEquipamentos.buscarPorId(Integer.toString(id));

        boolean existeRegistro = equipamento.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Equipamento NÃO cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(equipamento);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> getEquipamentoPorNomed(@PathVariable String nome) {
        Optional<Equipamento> equipamento = repositorioEquipamentos.buscarPorNome(nome);

        boolean existeRegistro = equipamento.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Equipamento NÃO cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(equipamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEquipamentoPorId(@PathVariable Integer id) {

        Optional<Equipamento> equipamento = repositorioEquipamentos.buscarPorId(Integer.toString(id));

        boolean existeRegistro = equipamento.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Equipamento NÃO cadastrado.\"}");
        }

        repositorioEquipamentos.remove(equipamento.get());
        return ResponseEntity.ok("{\"Mensagem\": \"Equipamento DELETADO com sucesso.\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alteraEquipamentoPorId(@PathVariable Integer id, @RequestBody EquipamentoForm equipamentoForm) {
        Map<Path, String> violacoesToMap = validar(equipamentoForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Equipamento equipamentoNew = equipamentoForm.toEquipamento();

        Optional<Equipamento> equipamentoOld = repositorioEquipamentos.buscarPorId(Integer.toString(id));
        boolean existeRegistro = equipamentoOld.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Equipamento NÃO cadastrado.\"}");
        }

        repositorioEquipamentos.altera(equipamentoOld.get(), equipamentoNew);
        return ResponseEntity.status(HttpStatus.OK).body(equipamentoNew);
    }


}
