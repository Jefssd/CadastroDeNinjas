package dev.jef.CadastroDeNinjas.missoes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissoesRepository extends JpaRepository<MissoesModel, Long> {
}
