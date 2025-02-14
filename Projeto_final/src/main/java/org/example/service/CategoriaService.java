package org.example.service;

import org.example.dao.CategoriaDAO;
import org.example.model.Categoria;
import org.example.exception.DataAccessException;

import java.util.List;

public class CategoriaService {

    private final CategoriaDAO categoriaDAO;

    public CategoriaService() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public void cadastrarCategoria(String nome) {
        Categoria categoria = new Categoria();
        categoria.setNome(nome);
        categoriaDAO.salvar(categoria);
    }

    public List<Categoria> listarCategorias() {
        return categoriaDAO.listarTodos();
    }

    public Categoria buscarCategoria(Long id) {
        return categoriaDAO.buscarPorId(id);
    }

    public void alterarCategoria(Categoria categoria) {
        categoriaDAO.atualizar(categoria);
    }

    public void excluirCategoria(Long id) {
        Categoria categoria = categoriaDAO.buscarPorId(id);
        if (categoria != null) {
            categoriaDAO.excluir(categoria);
        } else {
            throw new DataAccessException("Categoria não encontrada para exclusão.");
        }
    }
    public Categoria buscarCategoriaPorId(Long id) {
        try {
            return categoriaDAO.buscarPorId(id);  // Chama o método do DAO
        } catch (Exception e) {
            throw new DataAccessException("Erro ao buscar categoria por ID", e);
        }
    }
}
