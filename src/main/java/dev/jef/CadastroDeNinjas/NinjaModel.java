package dev.jef.CadastroDeNinjas;

import jakarta.persistence.*;

//Entity ele transforma uma classe em uma entidade do banco de dados
// JPA = java persistence API
@Entity
@Table(name = "tb_cadastro")
public class NinjaModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
       private  long id;
       private String nome;
       private String email;
       private int idade;

    // 1. Construtor vazio (Exigido pelo JPA/Hibernate)
    public NinjaModel(){

    }

    // 2. Construtor com parâmetros para facilitar a criação de novos objetos

    public NinjaModel(String nome, String email, int idade){
        this.nome =  nome;
        this.email = email;
        this.idade = idade;
    }

    // --- Getters e Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(){
        this.nome =  nome;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(){
        this.email =  email;
    }



}
