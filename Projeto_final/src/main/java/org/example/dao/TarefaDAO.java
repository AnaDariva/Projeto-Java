package org.example.dao;

import org.example.exception.DataAccessException;
import org.example.model.Categoria;
import org.example.model.Tarefa;
import org.example.model.Usuario;
import org.example.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class TarefaDAO {

    // Método para salvar a tarefa
    public void salvar(Tarefa tarefa) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            // Verificando e associando o usuário
            if (tarefa.getUsuario() != null && tarefa.getUsuario().getId() != null) {
                Usuario usuario = em.find(Usuario.class, tarefa.getUsuario().getId());
                if (usuario == null) {
                    throw new DataAccessException("Usuário não encontrado.");
                }
                tarefa.setUsuario(usuario);
            }

            // Verificando e associando a categoria
            if (tarefa.getCategoria() != null && tarefa.getCategoria().getId() != null) {
                Categoria categoria = em.find(Categoria.class, tarefa.getCategoria().getId());
                if (categoria == null) {
                    throw new DataAccessException("Categoria não encontrada.");
                }
                tarefa.setCategoria(categoria);
            }

            em.persist(tarefa);  // Persistindo a tarefa

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new DataAccessException("Erro ao salvar tarefa", e);
        } finally {
            em.close();  // Garantindo que o EntityManager seja fechado
        }
    }

    // Método para atualizar a tarefa
    public void atualizar(Tarefa tarefa) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(tarefa);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new DataAccessException("Erro ao atualizar tarefa", e);
        } finally {
            em.close();  // Garantindo que o EntityManager seja fechado
        }
    }

    // Método para excluir uma tarefa
    public void excluir(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Tarefa tarefaEncontrada = em.find(Tarefa.class, id);  // Buscando a tarefa pelo ID
            if (tarefaEncontrada != null) {
                em.remove(tarefaEncontrada);  // Remover a tarefa
            } else {
                throw new DataAccessException("Tarefa não encontrada para exclusão.");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new DataAccessException("Erro ao excluir tarefa", e);
        } finally {
            em.close();  // Garantindo que o EntityManager seja fechado
        }
    }

    // Método para buscar tarefa por ID
    public Tarefa buscarPorId(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Tarefa.class, id);
        } catch (Exception e) {
            throw new DataAccessException("Erro ao buscar tarefa por ID", e);
        } finally {
            em.close();  // Garantindo que o EntityManager seja fechado
        }
    }

    // Método para listar todas as tarefas
    public List<Tarefa> listarTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT t FROM Tarefa t", Tarefa.class).getResultList();
        } catch (Exception e) {
            throw new DataAccessException("Erro ao listar tarefas", e);
        } finally {
            em.close();  // Garantindo que o EntityManager seja fechado
        }
    }
}
