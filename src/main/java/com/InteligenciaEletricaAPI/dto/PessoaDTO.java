package com.InteligenciaEletricaAPI.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PessoaDTO {

    private Long id;
    private String nome;
    private String data_nascimento;
    private String sexo;
    private String codigo_cliente;
    private String relacionamento;

    public PessoaDTO() {

    }

    public PessoaDTO(String nome, String data_nascimento, String sexo, String codigo_cliente, String relacionamento) {
        this.nome = nome;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
        this.codigo_cliente = codigo_cliente;
        this.relacionamento = relacionamento;
    }

}
