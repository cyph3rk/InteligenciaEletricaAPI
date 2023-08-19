package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Pessoa;
import com.InteligenciaEletricaAPI.dto.PessoaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RepositorioPessoas extends JpaRepository<Pessoa, Long> {

    List<Pessoa> findByNome(String nome);

}
