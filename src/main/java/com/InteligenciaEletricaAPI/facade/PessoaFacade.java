package com.InteligenciaEletricaAPI.facade;

import com.InteligenciaEletricaAPI.dominio.Pessoa;
import com.InteligenciaEletricaAPI.dto.FamiliaDto;
import com.InteligenciaEletricaAPI.dto.PessoaDto;
import com.InteligenciaEletricaAPI.repositorio.IEnderecosRepositorio;
import com.InteligenciaEletricaAPI.repositorio.IEquipamentosRepositorio;
import com.InteligenciaEletricaAPI.repositorio.IFamiliasRepositorio;
import com.InteligenciaEletricaAPI.repositorio.IPessoasRepositorio;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaFacade {

    private static final Logger logger = LoggerFactory.getLogger(PessoaFacade.class);

    private final IPessoasRepositorio pessoasRepositorio;
    private final IEnderecosRepositorio enderecosRepositorio;

    private final IFamiliasRepositorio familiasRepositorio;

    @Autowired
    public PessoaFacade(IPessoasRepositorio pessoasRepositorio, IEnderecosRepositorio enderecosRepositorio,
                        IFamiliasRepositorio familiasRepositorio) {
        this.pessoasRepositorio = pessoasRepositorio;
        this.enderecosRepositorio = enderecosRepositorio;
        this.familiasRepositorio = familiasRepositorio;
    }

    public Long salvar(PessoaDto pessoaDto) {
        List<PessoaDto> encontrado = buscarPorNome(pessoaDto.getNome());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        var familia = familiasRepositorio.getReferenceById(pessoaDto.getFamiliaDto().getId());
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDto.getNome());
        pessoa.setData_nascimento(pessoaDto.getData_nascimento());
        pessoa.setSexo(pessoaDto.getSexo());
        pessoa.setCodigo_cliente(pessoaDto.getCodigo_cliente());
        pessoa.setRelacionamento(pessoaDto.getRelacionamento());
        pessoa.setFamilia(familia);

        pessoasRepositorio.save(pessoa);

        return pessoa.getId();
    }

    public List<PessoaDto> buscarPorNome(String nome) {
        List<Pessoa> listaPessoas = pessoasRepositorio.findByNome(nome);

        return listaPessoas.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public List<PessoaDto> listaFamiliaPorNome(String nome) {
        List<Pessoa> listaPessoas = pessoasRepositorio.buscaFamiliaPorNome(nome);

        return listaPessoas.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public List<PessoaDto> listaFamiliaPorId(long id) {
        List<Pessoa> listaPessoas = pessoasRepositorio.buscaFamiliaPorId(id);

        return listaPessoas.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<PessoaDto> buscarPorId(Long id) {

        try {
            Pessoa pessoa = pessoasRepositorio.getReferenceById(id);

            PessoaDto pessoaDto = new PessoaDto();
            pessoaDto.setId(pessoa.getId());
            pessoaDto.setNome(pessoa.getNome());
            pessoaDto.setData_nascimento(pessoa.getData_nascimento());
            pessoaDto.setSexo(pessoa.getSexo());
            pessoaDto.setCodigo_cliente(pessoa.getCodigo_cliente());
            pessoaDto.setRelacionamento(pessoa.getRelacionamento());

            FamiliaDto familiaDto = new FamiliaDto();
            familiaDto.setId(pessoa.getFamilia().getId());
            familiaDto.setNome(pessoa.getFamilia().getNome());
            pessoaDto.setFamiliaDto(familiaDto);

            return Optional.of(pessoaDto);
        } catch (EntityNotFoundException ex) {
            logger.info("PessoaFacade - buscarPorId Id: " + id + (" Não cadastrado"));
            return Optional.empty();
        }
    }

    public void remove(Long id) {
        //Todo: Implementar a verificação se cadastro existe antes de deletar
        pessoasRepositorio.deleteById(id);
    }

    //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
    public void altera(Long id, PessoaDto pessoaDto_New) {
        Pessoa pessoaDB = pessoasRepositorio.getReferenceById(id);
        pessoaDB.setNome(pessoaDto_New.getNome());
        pessoaDB.setData_nascimento(pessoaDto_New.getData_nascimento());
        pessoaDB.setSexo(pessoaDto_New.getSexo());
        pessoaDB.setCodigo_cliente(pessoaDto_New.getCodigo_cliente());
        pessoaDB.setRelacionamento(pessoaDto_New.getRelacionamento());

        pessoasRepositorio.save(pessoaDB);
    }

    private PessoaDto converter (Pessoa pessoa) {
        PessoaDto result = new PessoaDto();
        result.setId(pessoa.getId());
        result.setNome(pessoa.getNome());
        result.setData_nascimento(pessoa.getData_nascimento());
        result.setSexo(pessoa.getSexo());
        result.setCodigo_cliente(pessoa.getCodigo_cliente());
        result.setRelacionamento(pessoa.getRelacionamento());

        FamiliaDto familiaDto = new FamiliaDto();
        familiaDto.setId(pessoa.getFamilia().getId());
        familiaDto.setNome(pessoa.getFamilia().getNome());
        result.setFamiliaDto(familiaDto);

        return result;
    }

    public List<PessoaDto> getAll() {
        return pessoasRepositorio
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

}
