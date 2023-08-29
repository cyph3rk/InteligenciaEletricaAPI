package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEquipamentosRepositorio extends JpaRepository<Equipamento, Long> {

    List<Equipamento> findByNome(String nome);

}
