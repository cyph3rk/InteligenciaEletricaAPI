package com.InteligenciaEletricaAPI.dto;

import com.InteligenciaEletricaAPI.dominio.Familia;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FamiliaDto {
    private Long id;
    private String nome;

    public FamiliaDto() {

    }

    public FamiliaDto(String nome) {
        this.nome = nome;
    }

    public Familia toFamilia() {
        return new Familia(nome);
    }
}
