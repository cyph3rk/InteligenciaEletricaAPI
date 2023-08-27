package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPessoasRepositorio extends JpaRepository<Pessoa, Long> {

    List<Pessoa> findByNome(String nome);

    @Query("from Pessoa p where p.familia.id = :familia_id")
    List<Pessoa> buscaFamiliaPorId(Long familia_id);

    @Query("from Pessoa p where p.familia.nome = :nome")
    List<Pessoa> buscaFamiliaPorNome(String nome);

}
