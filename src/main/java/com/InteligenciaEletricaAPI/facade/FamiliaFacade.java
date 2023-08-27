package com.InteligenciaEletricaAPI.facade;

import com.InteligenciaEletricaAPI.dominio.Familia;
import com.InteligenciaEletricaAPI.dominio.Pessoa;
import com.InteligenciaEletricaAPI.dto.FamiliaDto;
import com.InteligenciaEletricaAPI.dto.PessoaDto;
import com.InteligenciaEletricaAPI.repositorio.IFamiliasRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FamiliaFacade {

    private static final Logger logger = LoggerFactory.getLogger(PessoaFacade.class);

    private final IFamiliasRepositorio familiasRepositorio;

    @Autowired
    public FamiliaFacade(IFamiliasRepositorio familiaRepositorio) {
        this.familiasRepositorio = familiaRepositorio;
    }

    public Long salvar(FamiliaDto familiaDto) {

        List<FamiliaDto> encontrado = buscarPorNome(familiaDto.getNome());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        Familia familia = new Familia();
        familia.setNome(familiaDto.getNome());

        familiasRepositorio.save(familia);

        return familia.getId();
    }

    public Long altera(Long id, FamiliaDto familiaDto_New) {

        List<FamiliaDto> familiaDtos = buscarPorNome(familiaDto_New.getNome());
        if (familiaDtos.size() >= 1) {
            return -1L;
        }

        Familia familia = familiasRepositorio.getReferenceById(id);
        familia.setNome(familiaDto_New.getNome());

        familiasRepositorio.save(familia);

        return familia.getId();
    }

    public List<FamiliaDto> buscarPorNome(String nome) {
        List<Familia> listaFamilia = familiasRepositorio.findByNome(nome);

        return listaFamilia.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<FamiliaDto> buscarPorId(Long id) {
        try {
            Familia familia = familiasRepositorio.getReferenceById(id);

            FamiliaDto familiaDto = new FamiliaDto();
            familiaDto.setId(familia.getId());
            familiaDto.setNome(familia.getNome());

            return Optional.of(familiaDto);
        } catch (EntityNotFoundException ex) {
            logger.info("PessoaFacade - buscarPorId Id: " + id + (" Não cadastrado"));
            return Optional.empty();
        }
    }

    private FamiliaDto converter(Familia familia) {
        FamiliaDto result = new FamiliaDto();
        result.setId(familia.getId());
        result.setNome(familia.getNome());

        return result;
    }


    public void remove(Long id) {
        familiasRepositorio.deleteById(id);
    }
}
