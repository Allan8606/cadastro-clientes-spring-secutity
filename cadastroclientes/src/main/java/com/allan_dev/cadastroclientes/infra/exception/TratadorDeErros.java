package com.allan_dev.cadastroclientes.infra.exception;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    // "Ei Spring, se estourar um EntityNotFoundException, cai aqui dentro!"
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        // Devolve o status 404 padrão, sem corpo (texto) nenhum.
        // TRATAMENTO 1: Erro 404 (Não Encontrado)
        // Acontece quando você tenta buscar/deletar um ID que não existe.
        return ResponseEntity.notFound().build();
    }

    // TRATAMENTO 2: Erro 400 (Dados Inválidos)
    // Acontece quando o usuário manda um JSON faltando campo ou com erro.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        // Pega a lista de erros (ex: "Email obrigatório", "Senha curta")
        // 1. Pega os erros técnicos de dentro da exceção
        var erros = ex.getFieldErrors();



        // 2. Transforma (mapeia) cada erro técnico no nosso DTO bonitinho
        // (Aqui usamos stream e map, que é um jeito chique de fazer um "for")
        var listaBonitinha = erros.stream()
                .map(DadosErroValidacao::new) // Converte Tecnico -> Bonito
                .toList();

        // 3. Devolve 400 (Bad Request) com a lista no corpo
        return ResponseEntity.badRequest().body(listaBonitinha);
    }

    // Um DTO interno (Record) só para devolver o erro bonitinho no JSON
    // (Você pode criar isso num arquivo separado em DTO se quiser, mas aqui fica mais prático)
    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }


}
