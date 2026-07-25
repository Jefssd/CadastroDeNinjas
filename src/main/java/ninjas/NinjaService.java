package ninjas;

import org.springframework.stereotype.Service;

@Service
public class NinjaService {

    private final NinjaRepository ninjaRepository;

    // Injeção de dependência via construtor
    public NinjaService(NinjaRepository ninjaRepository) {
        this.ninjaRepository = ninjaRepository;
    }

    // Aqui vão entrar as regras de negócio e chamadas ao repository
}
