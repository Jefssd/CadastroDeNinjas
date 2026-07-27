package dev.jef.CadastroDeNinjas.ninjas;
/*
 * MODEL (Entity)
 * Representa a tabela do banco de dados.
 * Define a estrutura dos dados (atributos/colunas) e seus relacionamentos.
 */
import dev.jef.CadastroDeNinjas.missoes.MissoesModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity ele transforma uma classe em uma entidade do banco de dados
// JPA = java persistence API
@Entity
@Table(name = "tb_cadastro")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class NinjaModel {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private  Long id;
       @NotBlank(message = "O nome é obrigatório")
       private String nome;

       @Email(message = "Email inválido")
       @NotBlank(message = "O email é obrigatório")
       private String email;

       @Min(value = 1, message = "A idade deve ser maior que zero")
       private int idade;

       //@ManyToOne Um ninja pode fazer apenas uma missão por vez
       @ManyToOne
       @JoinColumn(name = "missoes_id") //foreing key ou chave estrangeira.
       private MissoesModel missoes;



}
