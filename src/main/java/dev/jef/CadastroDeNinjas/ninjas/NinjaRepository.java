package dev.jef.CadastroDeNinjas.ninjas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/*
 * REPOSITORY OU INTERFACE
 * Camada de acesso ao banco de dados (DAO).
 * Herda os métodos do JpaRepository para realizar operações de CRUD sem SQL manual.
 */
@Repository
public interface NinjaRepository extends JpaRepository<NinjaModel, Long> {
}
