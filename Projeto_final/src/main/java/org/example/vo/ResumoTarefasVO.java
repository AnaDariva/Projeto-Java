package org.example.vo;

public class ResumoTarefasVO {

    private String nomeCategoria;
    private long totalTarefas;
    private long tarefasConcluidas;
    private long tarefasPendentes;

    public ResumoTarefasVO(String nomeCategoria, long totalTarefas, long tarefasConcluidas, long tarefasPendentes) {
        this.nomeCategoria = nomeCategoria;
        this.totalTarefas = totalTarefas;
        this.tarefasConcluidas = tarefasConcluidas;
        this.tarefasPendentes = tarefasPendentes;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public long getTotalTarefas() {
        return totalTarefas;
    }

    public void setTotalTarefas(long totalTarefas) {
        this.totalTarefas = totalTarefas;
    }

    public long getTarefasConcluidas() {
        return tarefasConcluidas;
    }

    public void setTarefasConcluidas(long tarefasConcluidas) {
        this.tarefasConcluidas = tarefasConcluidas;
    }

    public long getTarefasPendentes() {
        return tarefasPendentes;
    }

    public void setTarefasPendentes(long tarefasPendentes) {
        this.tarefasPendentes = tarefasPendentes;
    }

    @Override
    public String toString() {
        return "ResumoTarefasVO{" +
                "nomeCategoria='" + nomeCategoria + '\'' +
                ", totalTarefas=" + totalTarefas +
                ", tarefasConcluidas=" + tarefasConcluidas +
                ", tarefasPendentes=" + tarefasPendentes +
                '}';
    }
}
