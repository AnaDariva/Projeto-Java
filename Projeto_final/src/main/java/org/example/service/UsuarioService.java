package org.example.service;

import org.example.dao.UsuarioDAO;
import org.example.model.Usuario;

import java.util.List;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }
    public void alterarUsuario(Long id, String nome, String email) {
        // Lógica para alterar usuário
    }


    // Cadastrar novo usuário
    public void cadastrarUsuario(String nome, String email, String senha) {
        try {
            // Criando o usuário com nome, email e senha
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);  // Correção aqui, passando as Strings corretamente
            // Salvando o usuário no banco de dados
            usuarioDAO.salvar(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar usuário", e);
        }
    }

    // Buscar usuário por ID
    public Usuario buscarUsuario(Long id) {
        try {
            return usuarioDAO.buscarPorId(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário por ID", e);
        }
    }

    // Listar todos os usuários
    public List<Usuario> listarUsuarios() {
        try {
            return usuarioDAO.listarTodos();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }
    }

    // Excluir usuário por ID
    public void excluirUsuario(Long id) {
        try {
            usuarioDAO.excluir(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir usuário", e);
        }
    }
}
