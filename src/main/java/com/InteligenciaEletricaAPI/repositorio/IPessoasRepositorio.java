package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPessoasRepositorio extends JpaRepository<Pessoa, Long> {

    List<Pessoa> findByNome(String nome);

}
