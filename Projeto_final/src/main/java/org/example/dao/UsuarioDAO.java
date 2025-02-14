package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.exception.DataAccessException;
import org.example.model.Usuario;
import org.example.util.JpaUtil;

import java.util.List;

public class UsuarioDAO {

    public UsuarioDAO() {}

    // Salvar ou atualizar usuário
    public void salvar(Usuario usuario) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(usuario);  // Persiste o usuário
            transaction.commit();  // Confirma a transação!
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new DataAccessException("Erro ao salvar usuário", e);
        } finally {
            em.close();
        }
    }


    // Buscar usuário por ID
    public Usuario buscarPorId(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } catch (Exception e) {
            throw new DataAccessException("Erro ao buscar usuário por ID", e);
        } finally {
            em.close();
        }
    }

    // Listar todos os usuários
    public List<Usuario> listarTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataAccessException("Erro ao listar usuários", e);
        } finally {
            em.close();
        }
    }

    // Excluir usuário por ID
    public void excluir(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            Usuario usuario = buscarPorId(id);  // Reutilizando o método buscarPorId
            if (usuario != null) {
                transaction.begin();
                em.remove(em.contains(usuario) ? usuario : em.merge(usuario));
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataAccessException("Erro ao excluir usuário", e);
        } finally {
            em.close();
        }
    }
}
