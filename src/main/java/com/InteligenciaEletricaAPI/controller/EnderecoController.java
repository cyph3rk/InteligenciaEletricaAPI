package com.InteligenciaEletricaAPI.controller;


import com.InteligenciaEletricaAPI.controller.form.EnderecoForm;
import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.repositorio.RepositorioEnderecos;
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
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private RepositorioEnderecos repositorioEnderecos;

    @Autowired
    private Validator validator;

    private <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);

        return violacoes.stream().collect(Collectors.toMap(
                ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
    }

    @PostMapping
    public ResponseEntity cadastraEndereco(@RequestBody EnderecoForm enderecoForm) {
        Map<Path, String> violacoesToMap = validar(enderecoForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Endereco endereco = enderecoForm.toEndereco();
        Integer resp = repositorioEnderecos.salvar(endereco);
        if ( resp == -1) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Endereco JÁ cadastrado.\"}");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"Messagem\": \"Endereco CADASTRADO com sucesso.\", " +
                                                              "\"id\": \"" + resp +"\"}");
    }

    @GetMapping
    public ResponseEntity<String> getAllEnderecos() {

        String json = "Erro Inesperado";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(repositorioEnderecos.getAll());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(String.format(json));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEnderecoPorId(@PathVariable Integer id) {
        Optional<Endereco> endereco = repositorioEnderecos.buscarPorId(Integer.toString(id));

        boolean existeRegistro = endereco.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Endereco NÃO cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    //TODO: Melhor A a questão de busca por RUA. Ter só uma rua é estranho
    @GetMapping("/rua/{rua}")
    public ResponseEntity<Object> getEnderecoPorRua(@PathVariable String rua) {
        Optional<Endereco> endereco = repositorioEnderecos.buscarPorRua(rua);

        boolean existeRegistro = endereco.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Endereco NÃO cadastrado.\"}");
        }

        return ResponseEntity.status(HttpStatus.OK).body(endereco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEnderecoPorId(@PathVariable Integer id) {

        Optional<Endereco> endereco = repositorioEnderecos.buscarPorId(Integer.toString(id));

        boolean existeRegistro = endereco.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Endereco NÃO cadastrado.\"}");
        }

        repositorioEnderecos.remove(endereco.get());
        return ResponseEntity.ok("{\"Mensagem\": \"Endereco DELETADO com sucesso.\"}");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alteraEnderecoPorId(@PathVariable Integer id, @RequestBody EnderecoForm enderecoForm) {
        Map<Path, String> violacoesToMap = validar(enderecoForm);

        if (!violacoesToMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesToMap);
        }

        Endereco enderecoNew = enderecoForm.toEndereco();

        Optional<Endereco> enderecoOld = repositorioEnderecos.buscarPorId(Integer.toString(id));
        boolean existeRegistro = enderecoOld.isPresent();
        if (!existeRegistro) {
            return ResponseEntity.badRequest().body("{\"Erro\": \"Endereco NÃO cadastrado.\"}");
        }

        repositorioEnderecos.altera(enderecoOld.get(), enderecoNew);
        return ResponseEntity.status(HttpStatus.OK).body(enderecoNew);
    }


}
