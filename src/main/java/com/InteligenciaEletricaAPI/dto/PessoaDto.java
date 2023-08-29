package com.InteligenciaEletricaAPI.dto;

import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PessoaDto {
    private Long id;
    private String nome;
    private String data_nascimento;
    private String sexo;
    private String codigo_cliente;
    private String relacionamento;

    private FamiliaDto familiaDto;

    public PessoaDto() {

    }

    public PessoaDto(String nome, String data_nascimento, String sexo, String codigo_cliente, String relacionamento) {
        this.nome = nome;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
        this.codigo_cliente = codigo_cliente;
        this.relacionamento = relacionamento;
    }

    public PessoaDto(String nome, String data_nascimento, String sexo, String codigo_cliente,
                     String relacionamento, FamiliaDto familiaDto) {
        this.nome = nome;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
        this.codigo_cliente = codigo_cliente;
        this.relacionamento = relacionamento;
        this.familiaDto = familiaDto;
    }

}
