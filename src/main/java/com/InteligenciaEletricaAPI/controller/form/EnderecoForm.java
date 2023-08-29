package com.InteligenciaEletricaAPI.controller.form;

import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.dto.EnderecoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoForm {

    @JsonProperty
    @NotBlank(message = "Campo RUA é obrigatorio")
    private String rua;

    @JsonProperty
    @NotBlank(message = "Campo NUMERO é obrigatorio")
    private String numero;

    @JsonProperty
    @NotBlank(message = "Campo BAIRRO é obrigatorio")
    private String bairro;

    @JsonProperty
    @NotBlank(message = "Campo CIDADE é obrigatorio")
    private String cidade;

    @JsonProperty
    @NotBlank(message = "Campo ESTADO é obrigatorio")
    private String estado;

    public EnderecoDto toEnderecoDto() {
        return new EnderecoDto(rua, numero, bairro, cidade, estado);
    }
}
