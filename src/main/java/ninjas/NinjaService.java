package ninjas;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NinjaService {

    private final NinjaRepository ninjaRepository;

    public NinjaService(NinjaRepository ninjaRepository) {
        this.ninjaRepository = ninjaRepository;
    }

    // 1. Listar todos os ninjas
    public List<NinjaModel> listarNinjas() {
        return ninjaRepository.findAll();
    }

    // 2. Buscar um ninja por ID
    public NinjaModel buscarNinjaPorId(Long id) {
        Optional<NinjaModel> ninja = ninjaRepository.findById(id);
        return ninja.orElse(null); // Retorna o ninja se achar, ou null se não encontrar
    }

    // 3. Criar / Salvar um novo ninja
    public NinjaModel criarNinja(NinjaModel ninja) {
        return ninjaRepository.save(ninja);
    }

    // 4. Deletar um ninja por ID
    public void deletarNinjaPorId(Long id) {
        ninjaRepository.deleteById(id);
    }
}

