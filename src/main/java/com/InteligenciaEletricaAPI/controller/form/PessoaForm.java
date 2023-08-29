package com.InteligenciaEletricaAPI.controller.form;

import com.InteligenciaEletricaAPI.dominio.Pessoa;
import com.InteligenciaEletricaAPI.dto.FamiliaDto;
import com.InteligenciaEletricaAPI.dto.PessoaDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PessoaForm {

    @JsonProperty
    @NotBlank(message = "Campo NOME é obrigatorio")
    private String nome;

    @JsonProperty
    @NotBlank(message = "Campo DATA NASCIMENTO é obrigatorio")
    private String data_nascimento;

    @JsonProperty
    @NotBlank(message = "Campo SEXO é obrigatorio")
    private String sexo;

    @JsonProperty
    @NotBlank(message = "Campo CODIGO CLIENTE é obrigatorio")
    private String codigo_cliente;

    @JsonProperty
    @NotBlank(message = "Campo RELACIONAMENTO é obrigatorio")
    private String relacionamento;

    public PessoaDto toPessoaDto() {
        return new PessoaDto(nome, data_nascimento, sexo, codigo_cliente, relacionamento);
    }
}
