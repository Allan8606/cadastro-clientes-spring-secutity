package com.allan_dev.cadastroclientes.service;

import com.allan_dev.cadastroclientes.dto.request.FuncionarioRequest;
import com.allan_dev.cadastroclientes.dto.response.FuncionarioResponse;
import com.allan_dev.cadastroclientes.entity.Funcionario;
import com.allan_dev.cadastroclientes.mapper.FuncionarioMapper;
import com.allan_dev.cadastroclientes.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public FuncionarioResponse cadastrar(FuncionarioRequest funcionarioRequest){
        Funcionario funcionario = FuncionarioMapper.paraFuncionario(funcionarioRequest);

        String senhaCriptografada = passwordEncoder.encode(funcionario.getPassword());
        funcionario.setSenha(senhaCriptografada);
        funcionario.setPerfilFuncinario(funcionarioRequest.perfil());
        funcionarioRepository.save(funcionario);

        return FuncionarioMapper.paraFuncionarioResponse(funcionario);
    }

    public List<FuncionarioResponse> listarTodos(){
        return funcionarioRepository.findAll().stream()
                .map(funcionario -> FuncionarioMapper.paraFuncionarioResponse(funcionario)).toList();
    }

    public FuncionarioResponse buscarPorId(Long id){
       return funcionarioRepository.findById(id)
                .map(funcionario -> FuncionarioMapper.paraFuncionarioResponse(funcionario))
               .orElseThrow(() -> new EntityNotFoundException());
    }




}
