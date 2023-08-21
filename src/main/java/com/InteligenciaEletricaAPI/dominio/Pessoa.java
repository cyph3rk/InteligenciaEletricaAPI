package com.InteligenciaEletricaAPI.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @JsonProperty
    private String nome;

    @JsonProperty
    private String data_nascimento;

    @JsonProperty
    private String sexo;

    @JsonProperty
    private String codigo_cliente;

    @JsonProperty
    private String relacionamento;

    public Pessoa(String nome, String data_nascimento, String sexo, String codigo_cliente, String relacionamento) {
        this.nome = nome;
        this.data_nascimento = data_nascimento;
        this.sexo = sexo;
        this.codigo_cliente = codigo_cliente;
        this.relacionamento = relacionamento;
    }

    public Pessoa() {
        
    }
    public boolean identificadaPorNome(String nome) {
        return this.nome.equals(nome);
    }

    public boolean identificadaPorId(String id) {
        return this.id.equals(id);
    }
}
