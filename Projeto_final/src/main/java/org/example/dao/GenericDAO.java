package org.example.dao;

import org.example.exception.DataAccessException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.util.JpaUtil;

import java.util.List;

public class GenericDAO<T> {

    private final Class<T> clazz;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void salvar(T entity) throws DataAccessException {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entity);  // Persistindo a entidade
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();  // Rollback em caso de erro
            }
            throw new DataAccessException("Erro ao salvar a entidade", e);
        }
    }

    public void atualizar(T entity) throws DataAccessException {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(entity);  // Atualizando a entidade
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();  // Rollback em caso de erro
            }
            throw new DataAccessException("Erro ao atualizar a entidade", e);
        }
    }

    public List<T> listarTodos() throws DataAccessException {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e", clazz).getResultList();
        } catch (Exception e) {
            throw new DataAccessException("Erro ao listar entidades", e);
        }
    }

    public T buscarPorId(Long id) throws DataAccessException {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            T entity = em.find(clazz, id);  // Buscando a entidade
            if (entity == null) {
                throw new DataAccessException("Entidade não encontrada com o ID: " + id);
            }
            return entity;
        } catch (Exception e) {
            throw new DataAccessException("Erro ao buscar entidade por ID", e);
        }
    }

    public void excluir(T entity) throws DataAccessException {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));  // Excluindo a entidade
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();  // Rollback em caso de erro
            }
            throw new DataAccessException("Erro ao excluir a entidade", e);
        }
    }
}
