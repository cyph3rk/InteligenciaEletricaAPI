package com.InteligenciaEletricaAPI.controller.form;

import com.InteligenciaEletricaAPI.dto.FamiliaDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FamiliaForm {

    @JsonProperty
    @NotBlank(message = "Campo FAMILIA é obrigatorio")
    private String nome;

    public FamiliaDto familiaDto(){
        return new FamiliaDto(nome);
    }

}
