package com.allan_dev.cadastroclientes.service;

import com.allan_dev.cadastroclientes.dto.request.ClienteRequest;
import com.allan_dev.cadastroclientes.dto.response.ClienteResponse;
import com.allan_dev.cadastroclientes.entity.Cliente;
import com.allan_dev.cadastroclientes.mapper.ClienteMapper;
import com.allan_dev.cadastroclientes.repository.ClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponse cadastrar(ClienteRequest clienteRequest){
        Cliente cliente = ClienteMapper.paraCliente(clienteRequest);
        clienteRepository.save(cliente);
        return ClienteMapper.paraClienteResponse(cliente);
    }

    public List<ClienteResponse> listarTodos(){
        return clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::paraClienteResponse)
                .toList();

    }

    public ClienteResponse buscarPorId(Long id){
        return clienteRepository.findById(id)
                .map(cliente -> ClienteMapper.paraClienteResponse(cliente))
                 .orElseThrow(() -> new EntityNotFoundException());
    }
    public void deletar(Long id){
        clienteRepository.findById(id).ifPresent(cliente -> clienteRepository.delete(cliente));
    }


}
