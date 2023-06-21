package com.InteligenciaEletricaAPI.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Endereco {

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

    public boolean identificadaPor(String rua, String numero, String bairro, String cidade, String estado) {
        return this.rua.equals(rua)
                && this.numero.equals(numero)
                && this.bairro.equals(bairro)
                && this.cidade.equals(cidade)
                && this.estado.equals(estado);
    }
}
