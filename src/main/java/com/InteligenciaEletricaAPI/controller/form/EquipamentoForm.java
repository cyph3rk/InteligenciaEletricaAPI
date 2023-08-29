package com.InteligenciaEletricaAPI.controller.form;

import com.InteligenciaEletricaAPI.dominio.Equipamento;
import com.InteligenciaEletricaAPI.dto.EquipamentoDto;
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

    public EquipamentoDto toEquipamentoDto() {
        return new EquipamentoDto(nome, modelo, potencia);
    }
}
