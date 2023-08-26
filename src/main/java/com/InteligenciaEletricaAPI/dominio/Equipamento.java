package com.InteligenciaEletricaAPI.dominio;

import com.InteligenciaEletricaAPI.dto.EquipamentoDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "equipamento")
public class Equipamento {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @JsonProperty
    private String nome;

    @JsonProperty
    private String modelo;

    @JsonProperty
    private String potencia;

//    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
//    private Instant dataDeCriacao;

    @ManyToOne
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    public Equipamento() {

    }

    public Equipamento(String nome, String modelo, String potencia, Endereco endereco) {
        this.nome = nome;
        this.modelo = modelo;
        this.potencia = potencia;
        this.endereco = endereco;
    }

//    @PrePersist
//    public void prePersist() {
//        dataDeCriacao = Instant.now();
//    }

    public boolean identificadaPorNome(String nome) {
        return this.nome.equals(nome);
    }

    public boolean identificadaPorId(String id) {
        return this.id.equals(id);
    }

}
