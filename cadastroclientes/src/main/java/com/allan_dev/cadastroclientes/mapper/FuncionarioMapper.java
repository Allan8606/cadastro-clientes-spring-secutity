package com.allan_dev.cadastroclientes.mapper;

import com.allan_dev.cadastroclientes.dto.request.FuncionarioRequest;
import com.allan_dev.cadastroclientes.dto.response.FuncionarioResponse;
import com.allan_dev.cadastroclientes.entity.Funcionario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FuncionarioMapper {


    public static Funcionario paraFuncionario(FuncionarioRequest funcionarioRequest){
        return Funcionario.builder()
                .nome(funcionarioRequest.nome())
                .email(funcionarioRequest.email())
                .senha(funcionarioRequest.senha())
                .perfilFuncinario(funcionarioRequest.perfil())
                .build();
    }

    public static FuncionarioResponse paraFuncionarioResponse(Funcionario funcionario){

        return FuncionarioResponse.builder()
                .funcionarioId(funcionario.getFuncionarioId())
                .nome(funcionario.getNome())
                .email(funcionario.getEmail())
                .perfil(funcionario.getPerfilFuncinario())
                .senha(funcionario.getSenha())
                .build();
    }

}
