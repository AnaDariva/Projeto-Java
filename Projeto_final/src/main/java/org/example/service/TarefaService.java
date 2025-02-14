package org.example.service;

import org.example.dao.TarefaDAO;
import org.example.dao.UsuarioDAO;
import org.example.dao.CategoriaDAO;
import org.example.model.Tarefa;
import org.example.model.Categoria;
import org.example.model.Usuario;
import org.example.util.JpaUtil;
import org.example.vo.ResumoTarefasVO;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

public class TarefaService {

    private TarefaDAO tarefaDAO = new TarefaDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();  // Responsável por buscar Usuários
    private CategoriaDAO categoriaDAO = new CategoriaDAO();  // Responsável por buscar Categorias

    private EntityManager em = JpaUtil.getEntityManager();  // Para buscar as entidades

    // Cadastrar nova tarefa
    public void cadastrarTarefa(String nome, String descricao, Long usuarioId, Long categoriaId) {
        try {
            // Buscando o usuário e a categoria no banco de dados
            Usuario usuario = usuarioDAO.buscarPorId(usuarioId);  // Usando o método do UsuarioDAO
            Categoria categoria = categoriaDAO.buscarPorId(categoriaId);  // Usando o método do CategoriaDAO

            if (usuario == null) {
                throw new RuntimeException("Usuário não encontrado");
            }
            if (categoria == null) {
                throw new RuntimeException("Categoria não encontrada");
            }

            // Criando a tarefa com os dados fornecidos
            Tarefa tarefa = new Tarefa(nome, descricao, categoria);
            tarefa.setUsuario(usuario); // Atribuindo usuário à tarefa

            // Salvando a tarefa no banco de dados
            tarefaDAO.salvar(tarefa);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar tarefa", e);
        }
    }

    // Gerar relatório resumido de tarefas por categoria
    public List<ResumoTarefasVO> gerarRelatorioResumoTarefas() {
        List<Tarefa> tarefas = tarefaDAO.listarTodos();

        // Agrupar as tarefas por categoria
        return tarefas.stream()
                .collect(Collectors.groupingBy(t -> t.getCategoria().getNome())) // Agrupando por nome de categoria
                .entrySet().stream()
                .map(entry -> {
                    String categoria = entry.getKey();
                    List<Tarefa> tarefasPorCategoria = entry.getValue();
                    long totalTarefas = tarefasPorCategoria.size();
                    long tarefasConcluidas = tarefasPorCategoria.stream().filter(Tarefa::isConcluida).count();
                    long tarefasPendentes = totalTarefas - tarefasConcluidas;
                    return new ResumoTarefasVO(categoria, totalTarefas, tarefasConcluidas, tarefasPendentes);
                })
                .collect(Collectors.toList());
    }

    // Buscar tarefa por ID
    public Tarefa buscarTarefa(Long id) {
        try {
            return tarefaDAO.buscarPorId(id);  // Chama o método da DAO que já existe
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar tarefa", e);
        }
    }

    // Alterar tarefa existente (AGORA COM OBJETO TAREFA)
    public void alterarTarefa(Tarefa tarefa) {
        try {
            // Buscar a tarefa existente no banco
            Tarefa tarefaExistente = tarefaDAO.buscarPorId(tarefa.getId());
            if (tarefaExistente == null) {
                throw new RuntimeException("Tarefa não encontrada");
            }

            // Buscando o usuário e a categoria no banco de dados
            Usuario usuario = usuarioDAO.buscarPorId(tarefa.getUsuario().getId());
            Categoria categoria = categoriaDAO.buscarPorId(tarefa.getCategoria().getId());

            if (usuario == null) {
                throw new RuntimeException("Usuário não encontrado");
            }
            if (categoria == null) {
                throw new RuntimeException("Categoria não encontrada");
            }

            // Atualizando os dados da tarefa
            tarefaExistente.setNome(tarefa.getNome());
            tarefaExistente.setDescricao(tarefa.getDescricao());
            tarefaExistente.setUsuario(usuario);
            tarefaExistente.setCategoria(categoria);
            tarefaExistente.setConcluida(tarefa.isConcluida());

            // Salvando as mudanças no banco de dados
            tarefaDAO.atualizar(tarefaExistente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao alterar tarefa", e);
        }
    }
    public void excluirTarefa(Long id) {
        try {
            // Buscar a tarefa pelo ID
            Tarefa tarefa = tarefaDAO.buscarPorId(id);
            if (tarefa == null) {
                throw new RuntimeException("Tarefa não encontrada");
            }

            // Excluir a tarefa
            tarefaDAO.excluir(id); // Chama o método excluir da DAO
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir tarefa", e);
        }
    }
}
