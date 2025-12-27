package com.allan_dev.cadastroclientes.controller;


import com.allan_dev.cadastroclientes.dto.request.ClienteRequest;
import com.allan_dev.cadastroclientes.dto.response.ClienteResponse;
import com.allan_dev.cadastroclientes.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {


    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@RequestBody @Valid ClienteRequest clienteRequest){

        ClienteResponse cliente = clienteService.cadastrar(clienteRequest);

        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listarTodos(){
        List<ClienteResponse> listaClientes = clienteService.listarTodos();

        return ResponseEntity.ok(listaClientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id){
        ClienteResponse clienteResponse = clienteService.buscarPorId(id);

        return ResponseEntity.ok(clienteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){

        clienteService.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
