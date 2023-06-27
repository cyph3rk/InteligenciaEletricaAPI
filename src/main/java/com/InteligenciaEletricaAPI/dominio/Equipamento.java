package com.InteligenciaEletricaAPI.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Equipamento {

    @JsonProperty
    private String id;

    @JsonProperty
    private String nome;

    @JsonProperty
    private String modelo;

    @JsonProperty
    private String potencia;

    public Equipamento(String nome, String modelo, String potencia) {
        this.nome = nome;
        this.modelo = modelo;
        this.potencia = potencia;
    }

    public boolean identificadaPorNome(String nome) {
        return this.nome.equals(nome);
    }

    public boolean identificadaPorId(String id) {
        return this.id.equals(id);
    }

}
