package com.InteligenciaEletricaAPI.controller;

import com.InteligenciaEletricaAPI.controller.form.FamiliaForm;
import com.InteligenciaEletricaAPI.controller.form.PessoaForm;
import com.InteligenciaEletricaAPI.dto.FamiliaDto;
import com.InteligenciaEletricaAPI.dto.PessoaDto;
import com.InteligenciaEletricaAPI.facade.FamiliaFacade;
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
@RequestMapping("/familia")
public class FamiliaController {

    private static final Logger logger = LoggerFactory.getLogger(PessoaController.class);

    private final Validator validator;

    private final FamiliaFacade familiaFacade;

    @Autowired
    public FamiliaController(Validator validator, FamiliaFacade familiaFacade) {
        this.validator = validator;
        this.familiaFacade = familiaFacade;
    }

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping("/{familia}")
    public ResponseEntity<Object> cadastraFamilia(@PathVariable String familia) {

        logger.info("POST - Cadastro de uma nova Familia: Nome: " + familia);

        Map<Path, String> violacoesToMap = validar(familia);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        FamiliaDto familiaDto = new FamiliaDto(familia);
        Long resp = familiaFacade.salvar(familiaDto);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Familia JÁ cadastrado.\"}");
        }

        logger.info("POST - Sucesso : Cadastro Familia: Nome: " + familiaDto.getNome() + "Id: " + resp);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Familia CADASTRADA com sucesso.\", " +
                "\"id\": \"" + resp +"\"}");
    }

    @PutMapping("/{id}/{familia}")
    public ResponseEntity<Object> alteraFamiliaPorId(@PathVariable Long id, @PathVariable String familia) {

        logger.info("POST - Altera Familia: Nome: " + familia + " id: " + id);

        FamiliaDto familiaDto = new FamiliaDto(familia);
        Long resp = familiaFacade.altera(id, familiaDto);
        if (resp == -1L) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Familia NÃO cadastrado. Já existe!\"}");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Familia ALTERADA com sucesso.\", " +
                "\"id\": \"" + resp +"\"}");
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Object> getFamiliaPorNome(@PathVariable String nome) {

        List<FamiliaDto> familiaDtos = familiaFacade.buscarPorNome(nome);
        if (familiaDtos.size() == 0) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Familia NÃO cadastrada.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(familiaDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFamiliaPorId(@PathVariable Long id) {

        Optional<FamiliaDto> pessoaDto = familiaFacade.buscarPorId(id);

        boolean existeRegistro = pessoaDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Familia NÃO cadastrada.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(pessoaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFamiliaPorId(@PathVariable Long id) {

        Optional<FamiliaDto> familiaDto = familiaFacade.buscarPorId(id);

        boolean existeRegistro = familiaDto.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Familia NÃO cadastrada.\"}");
        }

        familiaFacade.remove(id);
        return ResponseEntity.ok("{\"Mensagem\": \"Familia DELETADA com sucesso.\"}");
    }

    @GetMapping
    public ResponseEntity<String> getAllFamilias() {
        logger.info("GET - Pedido de todas as Familias cadastradas");

        String json = "Erro Inesperado";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(familiaFacade.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(String.format(json));
    }

}
