package com.allan_dev.cadastroclientes.controller;


import com.allan_dev.cadastroclientes.dto.request.FuncionarioRequest;
import com.allan_dev.cadastroclientes.dto.response.FuncionarioResponse;
import com.allan_dev.cadastroclientes.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<FuncionarioResponse> cadastrar(@RequestBody FuncionarioRequest funcionarioRequest){
        FuncionarioResponse cadastrar = funcionarioService.cadastrar(funcionarioRequest);

        return ResponseEntity.ok(cadastrar);
    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponse>> listarTodos(){
        List<FuncionarioResponse> funcionarioResponses = funcionarioService.listarTodos();

        return ResponseEntity.ok(funcionarioResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable Long id){
        FuncionarioResponse funcionarioResponse = funcionarioService.buscarPorId(id);

        return ResponseEntity.ok(funcionarioResponse);
    }


}
