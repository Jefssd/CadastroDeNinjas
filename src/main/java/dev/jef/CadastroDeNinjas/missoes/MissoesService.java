package dev.jef.CadastroDeNinjas.missoes;


import dev.jef.CadastroDeNinjas.ninjas.NinjaModel;
import dev.jef.CadastroDeNinjas.ninjas.NinjaRepository;
import exceptions.MissaoNaoEncontradaException;
import exceptions.NinjaNaoEncontradoException;
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
        return missoesRepository.findById(id)
                .orElseThrow(() -> new MissaoNaoEncontradaException(id));
    }

    // 3. Criar / Salvar uma nova missão
    public MissoesModel criarMissao(MissoesModel missao) {
        return missoesRepository.save(missao);
    }

    // 4. Deletar um ninja por ID
    public void deletarMissaoPorId(Long id) {
        if (!missoesRepository.existsById(id)) {
            throw new MissaoNaoEncontradaException(id);
        }
        missoesRepository.deleteById(id);
    }

    // 5. Atualizar / Alterar dados da missao
    public MissoesModel atualizarMissao(Long id, MissoesModel missaoAtualizada) {
        MissoesModel missaoExistente = missoesRepository.findById(id)
                .orElseThrow(() -> new MissaoNaoEncontradaException(id));

        missaoExistente.setNome(missaoAtualizada.getNome());
        missaoExistente.setDificuldade(missaoAtualizada.getDificuldade());

        return missoesRepository.save(missaoExistente);
    }




}
