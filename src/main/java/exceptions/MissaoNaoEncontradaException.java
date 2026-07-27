package exceptions;

public class MissaoNaoEncontradaException extends RuntimeException {

    public MissaoNaoEncontradaException(Long id) {
        super("Missão com ID " + id + " não foi encontrada.");
    }
}