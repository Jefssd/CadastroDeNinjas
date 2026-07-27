package dev.jef.CadastroDeNinjas.ninjas;

import exceptions.NinjaNaoEncontradoException;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;
/*
 * SERVICE
 * Camada de regra de negócio da aplicação.
 * Processa as informações, aplica as regras necessárias e chama o Repository.
 */
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
        return ninjaRepository.findById(id)
                .orElseThrow(() -> new NinjaNaoEncontradoException(id));
    }

    // 3. Criar / Salvar um novo ninja
    public NinjaModel criarNinja(NinjaModel ninja) {
        return ninjaRepository.save(ninja);
    }

    // 4. Deletar um ninja por ID
    public void deletarNinjaPorId(Long id) {
        if (!ninjaRepository.existsById(id)) {
            throw new NinjaNaoEncontradoException(id);
        }
        ninjaRepository.deleteById(id);
    }

    // Atualizar / Alterar dados do ninja
    public NinjaModel atualizarNinja(Long id, NinjaModel ninjaAtualizado) {
        NinjaModel ninjaExistente = ninjaRepository.findById(id)
                .orElseThrow(() -> new NinjaNaoEncontradoException(id));

        ninjaExistente.setNome(ninjaAtualizado.getNome());
        // repita para os demais campos do seu NinjaModel (nivel, especialidade, etc.)

        return ninjaRepository.save(ninjaExistente);
    }
}

