package com.InteligenciaEletricaAPI.controller;

import com.InteligenciaEletricaAPI.controller.form.EquipamentoForm;
import com.InteligenciaEletricaAPI.dto.EnderecoDto;
import com.InteligenciaEletricaAPI.dto.EquipamentoDto;
import com.InteligenciaEletricaAPI.dto.PessoaDto;
import com.InteligenciaEletricaAPI.facade.EnderecoFacade;
import com.InteligenciaEletricaAPI.facade.EquipamentoFacade;
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
@RequestMapping("/equipamento")
public class EquipamentoController {

    private static final Logger logger = LoggerFactory.getLogger(EquipamentoController.class);

    private final EquipamentoFacade equipamentoFacade;
    private final EnderecoFacade enderecoFacade;

    private final Validator validator;

    @Autowired
    public EquipamentoController(EquipamentoFacade equipamentoFacade, EnderecoFacade enderecoFacade, Validator validator) {
        this.equipamentoFacade = equipamentoFacade;
        this.enderecoFacade = enderecoFacade;
        this.validator = validator;
    }


    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                        ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> cadastraEquipamento(@PathVariable Long id,
                                                      @RequestBody EquipamentoForm equipamentoForm) {

        logger.info("POST - Try : Cadastro de um novo Equipamento: Nome: " + equipamentoForm.getNome());

        Map<Path, String> violacoesToMap = validar(equipamentoForm);
        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Optional<EnderecoDto> enderecoDto = enderecoFacade.buscarPorId(id);
        boolean existeRegistro = enderecoDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Endereco NÃO cadastrado.\"}");
        }

        EquipamentoDto equipamentoDto = new EquipamentoDto(equipamentoForm.getNome(),
                equipamentoForm.getModelo(), equipamentoForm.getPotencia(), enderecoDto.get());
        Long resp = equipamentoFacade.salvar(equipamentoDto);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Equipamento JÁ cadastrado.\"}");
        }

        logger.info("POST - Sucesso : Cadastro Equipamento: Nome: " + equipamentoDto.getNome() + "Id: " + resp);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Equipamento CADASTRADO com sucesso.\", " +
                                                              "\"id\": \"" + resp +"\"}");
    }

    @GetMapping
    public ResponseEntity<String> getAllEquipamentos() {
        logger.info("GET - Pedido de todos os Equipamentos cadastrados");

        String json = "Erro Inesperado";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(equipamentoFacade.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(String.format(json));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEquipamentoPorId(@PathVariable Long id) {
        logger.info("GET - Pedido de Equipamento por Id: " + id);

        Optional<EquipamentoDto> equipamentoDto = equipamentoFacade.buscarPorId(id);

        boolean existeRegistro = equipamentoDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Equipamento NÃO cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(equipamentoDto);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> getEquipamentoPorNome(@PathVariable String nome) {

        List<EquipamentoDto> equipamentoDto = equipamentoFacade.buscarPorNome(nome);

        if (equipamentoDto.size() == 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Equipamento NÃO cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(equipamentoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEquipamentoPorId(@PathVariable Long id) {

        Optional<EquipamentoDto> equipamentoDto = equipamentoFacade.buscarPorId(id);

        boolean existeRegistro = equipamentoDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Equipamento NÃO cadastrado.\"}");
        }

        equipamentoFacade.remove(id);
        return ResponseEntity.ok("{\"Mensagem\": \"Equipamento DELETADO com sucesso.\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alteraEquipamentoPorId(@PathVariable Long id, @RequestBody EquipamentoForm equipamentoForm) {
        Map<Path, String> violacoesToMap = validar(equipamentoForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Optional<EquipamentoDto> equipamentoDto_old = equipamentoFacade.buscarPorId(id);
        boolean existeRegistro = equipamentoDto_old.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Equipamento NÃO cadastrado.\"}");
        }

        EquipamentoDto equipamentoDto_new = equipamentoForm.toEquipamentoDto();
        Long resp = equipamentoFacade.altera(id, equipamentoDto_new);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Pessoa JÁ cadastrado.\"}");
        }

        logger.info("POST - Sucesso : Cadastro Pessoa: Nome: " + equipamentoDto_new.getNome() + "Id: " + resp);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Equipamento ALTERADO com sucesso.\", " +
                "\"id\": \"" + resp +"\"}");
    }


}
