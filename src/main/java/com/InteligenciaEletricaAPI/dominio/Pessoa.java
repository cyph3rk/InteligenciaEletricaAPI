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

    public boolean identificadaPorNome(String nome) {
        return this.nome.equals(nome);
    }

    public boolean identificadaPorId(String id) {
        return this.id.equals(id);
    }
}
