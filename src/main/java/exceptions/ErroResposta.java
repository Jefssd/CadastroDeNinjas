package exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
public class ErroResposta {

    private LocalDateTime timestamp;
    private int status;
    private String mensagem;

    public ErroResposta(int status, String mensagem) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.mensagem = mensagem;
    }


}