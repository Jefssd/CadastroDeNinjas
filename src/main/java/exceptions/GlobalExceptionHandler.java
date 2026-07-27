package dev.jef.CadastroDeNinjas.exceptions;

import exceptions.ErroResposta;
import exceptions.MissaoNaoEncontradaException;
import exceptions.NinjaNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura quando um ninja não é encontrado (404)
    @ExceptionHandler(NinjaNaoEncontradoException.class)
    public ResponseEntity<ErroResposta> handleNinjaNaoEncontrado(NinjaNaoEncontradoException ex) {
        ErroResposta erro = new ErroResposta(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // Captura falhas de validação do @Valid (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleValidacao(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(erro -> erro.getField() + ": " + erro.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErroResposta erro = new ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Captura qualquer outra exceção não tratada (500) — fica por último, é o "pega tudo"
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGenerica(Exception ex) {
        ErroResposta erro = new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno no servidor: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

    @ExceptionHandler(MissaoNaoEncontradaException.class)
    public ResponseEntity<ErroResposta> handleMissaoNaoEncontrada(MissaoNaoEncontradaException ex) {
        ErroResposta erro = new ErroResposta(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }
}