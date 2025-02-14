package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")  // Nome da tabela no banco de dados
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Geração automática do ID
    private Long id;

    @Column(name = "nome", nullable = false)  // Nome obrigatório
    private String nome;

    @Column(name = "email", nullable = false, unique = true)  // Email único e obrigatório
    private String email;

    @Column(name = "senha", nullable = false)  // Senha obrigatória
    private String senha;

    // Método toString para facilitar a exibição no console
    @Override
    public String toString() {
        return "Usuário [id=" + id + ", nome=" + nome + ", email=" + email + "]";
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
