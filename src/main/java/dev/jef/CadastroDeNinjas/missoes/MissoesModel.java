package dev.jef.CadastroDeNinjas.missoes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.jef.CadastroDeNinjas.ninjas.NinjaModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tab_missoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissoesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da missão é obrigatório")
    private String nome;

    @NotBlank(message = "A dificuldade é obrigatória")
    private String dificuldade;

    //@OneToMany muitos ninja podem fazer a mesma missão
    @OneToMany(mappedBy = "missoes")
    @JsonIgnore
    private List<NinjaModel> ninja;
}
