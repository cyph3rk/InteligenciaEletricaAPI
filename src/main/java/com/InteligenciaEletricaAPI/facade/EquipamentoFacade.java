package com.InteligenciaEletricaAPI.facade;

import com.InteligenciaEletricaAPI.dominio.Endereco;
import com.InteligenciaEletricaAPI.dominio.Equipamento;
import com.InteligenciaEletricaAPI.dto.EnderecoDto;
import com.InteligenciaEletricaAPI.dto.EquipamentoDto;
import com.InteligenciaEletricaAPI.repositorio.IEnderecosRepositorio;
import com.InteligenciaEletricaAPI.repositorio.IEquipamentosRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipamentoFacade {

    private static final Logger logger = LoggerFactory.getLogger(PessoaFacade.class);

    private final IEquipamentosRepositorio equipamentosRepositorio;
    private final IEnderecosRepositorio enderecosRepositorio;

    @Autowired
    public EquipamentoFacade(IEquipamentosRepositorio equipamentosRepositorio, IEnderecosRepositorio enderecosRepositorio) {
        this.equipamentosRepositorio = equipamentosRepositorio;
        this.enderecosRepositorio = enderecosRepositorio;
    }

    public Long salvar(EquipamentoDto equipamentoDto) {

        List<EquipamentoDto> encontrado = buscarPorNome(equipamentoDto.getNome());
        if (encontrado.size() >= 1) {
            return -1L;
        }

        var endereco = enderecosRepositorio.getReferenceById(equipamentoDto.getEnderecoDto().getId());
        Equipamento equipamento = new Equipamento();
        equipamento.setNome(equipamentoDto.getNome());
        equipamento.setModelo(equipamentoDto.getModelo());
        equipamento.setPotencia(equipamentoDto.getPotencia());
        equipamento.setEndereco(endereco);

        equipamentosRepositorio.save(equipamento);

        return equipamento.getId();
    }

//    @Transactional
//    public EquipamentoDto save(EquipamentoDto equipamentoDto) {
//        var equipamento = EquipamentoDto.toEquipamento(equipamentoDto);
//        var enderecoSaved = repositorioEquipamentos.save(equipamento);
//
//        return EquipamentoDto.fromEntity(enderecoSaved);
//    }

    public List<EquipamentoDto> buscarPorNome(String nome) {

        List<Equipamento> listaPessoas = equipamentosRepositorio.findByNome(nome);

        return listaPessoas.stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<EquipamentoDto> buscarPorId(Long id) {

        try {
            Equipamento equipamento = equipamentosRepositorio.getReferenceById(id);

            EquipamentoDto equipamentoDto = new EquipamentoDto();
            equipamentoDto.setId(equipamento.getId());
            equipamentoDto.setNome(equipamento.getNome());
            equipamentoDto.setModelo(equipamento.getModelo());
            equipamentoDto.setPotencia(equipamento.getPotencia());

            EnderecoDto enderecoDto = new EnderecoDto();
            enderecoDto.setId(equipamento.getEndereco().getId());
            enderecoDto.setRua(equipamento.getEndereco().getRua());
            enderecoDto.setNumero(equipamento.getEndereco().getNumero());
            enderecoDto.setBairro(equipamento.getEndereco().getBairro());
            enderecoDto.setCidade(equipamento.getEndereco().getCidade());
            enderecoDto.setEstado(equipamento.getEndereco().getEstado());
            equipamentoDto.setEnderecoDto(enderecoDto);

            return Optional.of(equipamentoDto);
        } catch (EntityNotFoundException ex) {
            logger.info("PessoaFacade - buscarPorId Id: " + id + (" Não cadastrado"));
            return Optional.empty();
            //todo: fazer o lancamento exception 35:00
        }
    }
//    @Transactional(readOnly = true)
//    public EquipamentoDto findById(Long id) {
//        var equipamento = repositorioEquipamentos.findById(id).orElseThrow(
//                () -> new ControllerNotFoundException("Endereço nao encontrado")
//        );
//
//        return  EquipamentoDto.fromEntity(equipamento);
//    }

    public void remove(Long id) {
        //Todo: Implementar a verificação se cadastro existe antes de deletar
        equipamentosRepositorio.deleteById(id);
    }

//    public void delete(Long id) {
//        try {
//            repositorioEquipamentos.deleteById(id);
//        } catch (DataIntegrityViolationException e) {
//            throw new DatabaseException("Violacao de integridade dos dados");
//        }
//    }

    //TODO: Resolver o problema de alterar o nome para um que ja existe quebrando a regra de duplicidade
    public Long altera(Long id, EquipamentoDto equipamentoDto_New) {

        Equipamento equipamento = equipamentosRepositorio.getReferenceById(id);

        equipamento.setNome(equipamentoDto_New.getNome());
        equipamento.setModelo(equipamentoDto_New.getModelo());
        equipamento.setPotencia(equipamentoDto_New.getPotencia());

        equipamentosRepositorio.save(equipamento);

        return equipamento.getId();
    }

//    @Transactional
//    public EquipamentoDto altera(Long Id, EquipamentoDto equipamentoDto) {
//        try {
//            Equipamento equipamento = repositorioEquipamentos.getReferenceById(Id);
//            EquipamentoDto.mapperDtoToEntity(equipamentoDto, equipamento);
//            equipamento = repositorioEquipamentos.save(equipamento);
//            return EquipamentoDto.fromEntity(equipamento);
//
//        } catch (EntityNotFoundException e)
//            throw new ControllerNotFoundException("Endereço nao encontrado, id: " + Id);
//    }

    private EquipamentoDto converter (Equipamento equipamento) {
        EquipamentoDto result = new EquipamentoDto();
        result.setId(equipamento.getId());
        result.setNome(equipamento.getNome());
        result.setModelo(equipamento.getModelo());
        result.setPotencia(equipamento.getPotencia());

        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setId(equipamento.getEndereco().getId());
        enderecoDto.setRua(equipamento.getEndereco().getRua());
        enderecoDto.setNumero(equipamento.getEndereco().getNumero());
        enderecoDto.setBairro(equipamento.getEndereco().getBairro());
        enderecoDto.setCidade(equipamento.getEndereco().getCidade());
        enderecoDto.setEstado(equipamento.getEndereco().getEstado());
        result.setEnderecoDto(enderecoDto);

        return result;
    }


//    public List<EquipamentoEnderecoDto> getAll() {
//        var equipamento = equipamentosRepositorio.findAll();
//        return equipamento.map(EquipamentoDto::fromEntity);
//    }

    public List<EquipamentoDto> getAll() {
        return equipamentosRepositorio
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

}














