package com.InteligenciaEletricaAPI.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pessoa {

    @JsonProperty
    private String id;

    @JsonProperty
    private String nome;

    @JsonProperty
    private String dataNascimento;

    @JsonProperty
    private String sexo;

    @JsonProperty
    private String parentesco;

    public Pessoa(String nome, String dataNascimento, String sexo, String parentesco) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.parentesco = parentesco;
    }

    public boolean identificadaPor(String nome, String dataNascimento, String sexo, String parentesco) {
        return this.nome.equals(nome)
                && this.dataNascimento.equals(dataNascimento)
                && this.sexo.equals(sexo)
                && this.parentesco.equals(parentesco);
    }
}
