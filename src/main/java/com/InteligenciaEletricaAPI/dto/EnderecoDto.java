package com.InteligenciaEletricaAPI.dto;

import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoDto {
    private Long id;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;

    public EnderecoDto() {

    }

    public EnderecoDto(String rua, String numero, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public Endereco toEndereco() {
        return new Endereco(rua, numero, bairro, cidade, estado);
    }
}
