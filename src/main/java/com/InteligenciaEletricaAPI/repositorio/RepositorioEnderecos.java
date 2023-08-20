package com.InteligenciaEletricaAPI.repositorio;

import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RepositorioEnderecos extends JpaRepository<Endereco, Long> {

    List<Endereco> findByRua(String rua);

//    public Integer salvar(Endereco endereco) {
//        boolean encontrado = enderecos.stream().anyMatch(end -> end.getRua().equals(endereco.getRua()));
//        if (encontrado) {
//            return -1;
//        }
//
//        endereco.setId(Integer.toString(getId()));
//        enderecos.add(endereco);
//        return Integer.parseInt(endereco.getId());
//    }
//
//    public Optional<Endereco> buscarPorId(String id) {
//        return enderecos.stream()
//                .filter(endereco -> endereco.identificadaPorId(id))
//                .findFirst();
//    }
//
//    public Optional<Endereco> buscarPorRua(String rua) {
//        return enderecos.stream()
//                .filter(endereco -> endereco.identificadaPorRua(rua))
//                .findFirst();
//    }
//
//    public Object getAll() {
//        return enderecos;
//    }
//
//    public void remove(Endereco endereco) {
//        enderecos.remove(endereco);
//    }
//
//    //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
//    public void altera(Endereco enderecoOld, Endereco enderecoNew) {
//        remove(enderecoOld);
//        enderecoNew.setId(enderecoOld.getId());
//        enderecos.add(enderecoNew);
//    }

}
