package com.allan_dev.cadastroclientes.mapper;


import com.allan_dev.cadastroclientes.dto.request.ClienteRequest;
import com.allan_dev.cadastroclientes.dto.response.ClienteResponse;
import com.allan_dev.cadastroclientes.entity.Cliente;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClienteMapper {


    public static Cliente paraCliente(ClienteRequest request){

        return Cliente
                .builder()
                .nome(request.nome())
                .email(request.email())
                .endereco(request.endereco())
                .build();
    }

    public static ClienteResponse paraClienteResponse(Cliente cliente){

        return ClienteResponse
                .builder()
                .funcionarioId(cliente.getClienteId())
                .nome(cliente.getNome())
                .email(cliente.getEmail())
                .endereco(cliente.getEndereco())
                .build();
    }


}
