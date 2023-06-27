package com.InteligenciaEletricaAPI.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Endereco {

    @JsonProperty
    private String id;

    @JsonProperty
    private String rua;

    @JsonProperty
    private String numero;

    @JsonProperty
    private String bairro;

    @JsonProperty
    private String cidade;

    @JsonProperty
    private String estado;

    public Endereco(String rua, String numero, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public boolean identificadaPorRua(String rua) {
        return this.rua.equals(rua);
    }

    public boolean identificadaPorId(String id) {
        return this.id.equals(id);
    }
}
