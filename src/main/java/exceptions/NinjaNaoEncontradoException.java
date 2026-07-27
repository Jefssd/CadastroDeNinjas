package exceptions;

public class NinjaNaoEncontradoException extends RuntimeException {
    public NinjaNaoEncontradoException(Long id){
        super("Ninja com ID " + id + " não foi encontrado.");
    }

}
