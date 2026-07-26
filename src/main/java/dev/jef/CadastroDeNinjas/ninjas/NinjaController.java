package dev.jef.CadastroDeNinjas.ninjas;

import org.springframework.web.bind.annotation.*;
import java.util.List;
/*
 * CONTROLLER
 * Porta de entrada da API (Gerenciador de Rotas/Endpoints).
 * Recebe as requisições HTTP, repassa para o Service e retorna as respostas em JSON.
 */
@RestController
@RequestMapping("/ninjas")
public class NinjaController {

    private final NinjaService ninjaService;

    // Injeção de dependência do NinjaService
    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    // 1. Endpoint para criar um novo ninja (POST /ninjas)
    @PostMapping
    public NinjaModel criarNinja(@RequestBody NinjaModel ninja) {
        return ninjaService.criarNinja(ninja);
    }

    // 2. Endpoint para listar todos os ninjas (GET /ninjas)
    @GetMapping
    public List<NinjaModel> listarNinjas() {
        return ninjaService.listarNinjas();
    }

    // 3. Endpoint para buscar ninja por ID (GET /ninjas/{id})
    @GetMapping("/{id}")
    public NinjaModel buscarNinjaPorId(@PathVariable Long id) {
        return ninjaService.buscarNinjaPorId(id);
    }

    // 4. Endpoint para deletar ninja por ID (DELETE /ninjas/{id})
    @DeleteMapping("/{id}")
    public void deletarNinjaPorId(@PathVariable Long id) {
        ninjaService.deletarNinjaPorId(id);
    }
}