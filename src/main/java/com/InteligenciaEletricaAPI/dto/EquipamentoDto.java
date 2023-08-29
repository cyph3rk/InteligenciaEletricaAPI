package com.InteligenciaEletricaAPI.dto;

import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.dominio.Equipamento;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EquipamentoDto {
    private Long id;
    private String nome;
    private String modelo;
    private String potencia;

    private EnderecoDto enderecoDto;

    public EquipamentoDto() {

    }

    public EquipamentoDto(String nome, String modelo, String potencia) {
        this.nome = nome;
        this.modelo = modelo;
        this.potencia = potencia;
    }

    public EquipamentoDto(String nome, String modelo, String potencia, EnderecoDto enderecoDto) {
        this.nome = nome;
        this.modelo = modelo;
        this.potencia = potencia;
        this.enderecoDto = enderecoDto;
    }

//    public Equipamento toEquipamento() {
//        return new Equipamento(nome, modelo, potencia, enderecoDto.toEndereco());
//    }

//    public Equipamento toEquipamento(EquipamentoDto equipamentoDto, Endereco endereco) {
//        Equipamento equipamento = new Equipamento(nome, modelo, potencia, endereco);
//        return equipamento;
//    }
//
//    public Equipamento toEquipamento(EquipamentoEnderecoDto equipamentoEnderecoDto) {
//        Equipamento equipamento =  new Equipamento(nome, modelo, potencia);
//        equipamento.setId(equipamentoEnderecoDto.getId());
//        equipamento.setEndereco(enderecoDto.toEndereco());
//        return equipamento;
//    }

}
