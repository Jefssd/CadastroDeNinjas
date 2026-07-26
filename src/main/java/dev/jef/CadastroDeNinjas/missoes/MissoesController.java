package dev.jef.CadastroDeNinjas.missoes;

import dev.jef.CadastroDeNinjas.ninjas.NinjaModel;
import dev.jef.CadastroDeNinjas.ninjas.NinjaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissoesController {

    private  final MissoesService missoesService;

    // Injeção de dependência do MissoesService
    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    // 1. Endpoint para criar uma nova missao (POST /missoes)
    @PostMapping
    public MissoesModel criarMissao(@RequestBody MissoesModel missao) {
        return missoesService.criarMissao(missao);
    }

    // 2. Endpoint para listar todos os ninjas (GET /missoes)
    @GetMapping
    public List<MissoesModel> listarMissoes() {
        return missoesService.listarMissoes();
    }

    // 3. Endpoint para buscar missao por ID (GET /missoes/{id})
    @GetMapping("/{id}")
    public MissoesModel buscarMissaoPorId(@PathVariable Long id) {
        return missoesService.buscarMissaoPorId(id);
    }

    // 4. Endpoint para deletar missao por ID (DELETE /missoes/{id})
    @DeleteMapping("/{id}")
    public void deletarMissaoPorId(@PathVariable Long id) {
        missoesService.deletarMissaoPorId(id);
    }
}
