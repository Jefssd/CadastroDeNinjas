package dev.jef.CadastroDeNinjas.missoes;


import dev.jef.CadastroDeNinjas.ninjas.NinjaModel;
import dev.jef.CadastroDeNinjas.ninjas.NinjaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MissoesService {

    private final MissoesRepository missoesRepository;

    public MissoesService(MissoesRepository missoesRepository) {this.missoesRepository = missoesRepository;}

    // 1. Listar todas as missoes
    public List<MissoesModel> listarMissoes() {
        return missoesRepository.findAll();
    }

    // 2. Buscar uma missao por ID
    public MissoesModel buscarMissaoPorId(Long id) {
        Optional<MissoesModel> missao = missoesRepository.findById(id);
        return missao.orElse(null); // Retorna a missao se achar, ou null se não encontrar
    }

    // 3. Criar / Salvar uma nova missão
    public MissoesModel criarMissao(MissoesModel missao) {
        return missoesRepository.save(missao);
    }

    // 4. Deletar um ninja por ID
    public void deletarMissaoPorId(Long id) {
        missoesRepository.deleteById(id);
    }




}
