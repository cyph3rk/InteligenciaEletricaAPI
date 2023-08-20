package com.InteligenciaEletricaAPI.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDTO {

    private Long id;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;

    public EnderecoDTO() {

    }

    public EnderecoDTO(String rua, String numero, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

}
