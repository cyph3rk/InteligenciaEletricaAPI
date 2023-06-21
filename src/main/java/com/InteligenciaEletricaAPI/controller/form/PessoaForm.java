package com.InteligenciaEletricaAPI.controller.form;

import com.InteligenciaEletricaAPI.dominio.Pessoa;
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
    private String dataNascimento;

    @JsonProperty
    @NotBlank(message = "Campo SEXO é obrigatorio")
    private String sexo;

    @JsonProperty
    @NotBlank(message = "Campo PARENTESCO é obrigatorio")
    private String parentesco;

    public Pessoa toPessoa() {
        return new Pessoa(nome, dataNascimento, sexo, parentesco);
    }
}
