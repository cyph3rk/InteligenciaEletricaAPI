package com.InteligenciaEletricaAPI.controller.form;

import com.InteligenciaEletricaAPI.dominio.Equipamento;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EquipamentoForm {

    @JsonProperty
    @NotBlank(message = "Campo NOME é obrigatorio")
    private String nome;

    @JsonProperty
    @NotBlank(message = "Campo MODELO é obrigatorio")
    private String modelo;

    @JsonProperty
    @NotBlank(message = "Campo POTENCIA é obrigatorio")
    private String potencia;

    public Equipamento toEquipamento() {
        return new Equipamento(nome, modelo, potencia);
    }
}
