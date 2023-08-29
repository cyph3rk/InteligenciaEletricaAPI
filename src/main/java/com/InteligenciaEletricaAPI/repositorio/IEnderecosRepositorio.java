package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEnderecosRepositorio extends JpaRepository<Endereco, Long> {

    List<Endereco> findByRua(String rua);

}
