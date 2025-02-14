package org.example.dao;

import org.example.exception.DataAccessException;
import org.example.model.Categoria;

import java.util.List;

public class CategoriaDAO {

    private final GenericDAO<Categoria> genericDAO;

    public CategoriaDAO() {
        this.genericDAO = new GenericDAO<>(Categoria.class);
    }

    public void salvar(Categoria categoria) {
        try {
            genericDAO.salvar(categoria);  // Delegando a persistência para o GenericDAO
        } catch (Exception e) {
            throw new DataAccessException("Erro ao salvar categoria", e);
        }
    }

    public void atualizar(Categoria categoria) {
        try {
            genericDAO.atualizar(categoria);  // Delegando a atualização para o GenericDAO
        } catch (Exception e) {
            throw new DataAccessException("Erro ao atualizar categoria", e);
        }
    }

    public List<Categoria> listarTodos() {
        try {
            return genericDAO.listarTodos();  // Delegando a listagem para o GenericDAO
        } catch (Exception e) {
            throw new DataAccessException("Erro ao listar categorias", e);
        }
    }

    public Categoria buscarPorId(Long id) {
        try {
            return genericDAO.buscarPorId(id);  // Delegando a busca para o GenericDAO
        } catch (Exception e) {
            throw new DataAccessException("Erro ao buscar categoria por ID", e);
        }
    }

    public void excluir(Categoria categoria) {
        try {
            genericDAO.excluir(categoria);  // Delegando a exclusão para o GenericDAO
        } catch (Exception e) {
            throw new DataAccessException("Erro ao excluir categoria", e);
        }
    }
}
