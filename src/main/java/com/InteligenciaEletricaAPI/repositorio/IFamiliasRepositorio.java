package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Familia;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFamiliasRepositorio extends JpaRepository<Familia, Long> {

    List<Familia> findByNome(String nome);

}
