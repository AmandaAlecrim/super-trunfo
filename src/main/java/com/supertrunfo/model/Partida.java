package com.supertrunfo.model;

import java.util.Date;

public class Partida {
    private int id;
    private int roundsVencidosJogador;
    private int roundsVencidosCPU;
    private int roundsEmpatados;
    private String resultado;
    private Date data;
    private boolean forcaUtilizada;
    private boolean inteligenciaUtilizada;
    private boolean velocidadeUtilizada;

    public Partida() {
        this.roundsVencidosJogador = 0; // Valor padrão para os rounds vencidos pelo jogador
        this.roundsVencidosCPU = 0; // Valor padrão para os rounds vencidos pela CPU
        this.roundsEmpatados = 0; // Valor padrão para os rounds empatados
        this.resultado = "Em andamento"; // Resultado vazio
        this.data = new Date(); // Data de hoje
        this.forcaUtilizada = false; // Forca não utilizada
        this.inteligenciaUtilizada = false; // Inteligência não utilizada
        this.velocidadeUtilizada = false; // Velocidade não utilizada
    }

    public Partida(int id, int roundsVencidosJogador, int roundsVencidosCPU, int roundsEmpatados, String resultado, Date data, boolean forcaUtilizada, boolean inteligenciaUtilizada, boolean velocidadeUtilizada) {
        this.id = id;
        this.roundsVencidosJogador = roundsVencidosJogador;
        this.roundsVencidosCPU = roundsVencidosCPU;
        this.roundsEmpatados = roundsEmpatados;
        this.resultado = resultado;
        this.data = data;
        this.forcaUtilizada = forcaUtilizada;
        this.inteligenciaUtilizada = inteligenciaUtilizada;
        this.velocidadeUtilizada = velocidadeUtilizada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoundsVencidosJogador() {
        return roundsVencidosJogador;
    }

    public void setRoundsVencidosJogador(int roundsVencidosJogador) {
        this.roundsVencidosJogador = roundsVencidosJogador;
    }

    public int getRoundsVencidosCPU() {
        return roundsVencidosCPU;
    }

    public void setRoundsVencidosCPU(int roundsVencidosCPU) {
        this.roundsVencidosCPU = roundsVencidosCPU;
    }

    public int getRoundsEmpatados() {
        return roundsEmpatados;
    }

    public void setRoundsEmpatados(int roundsEmpatados) {
        this.roundsEmpatados = roundsEmpatados;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public boolean isForcaUtilizada() {
        return forcaUtilizada;
    }

    public void setForcaUtilizada(boolean forcaUtilizada) {
        this.forcaUtilizada = forcaUtilizada;
    }

    public boolean isInteligenciaUtilizada() {
        return inteligenciaUtilizada;
    }

    public void setInteligenciaUtilizada(boolean inteligenciaUtilizada) {
        this.inteligenciaUtilizada = inteligenciaUtilizada;
    }

    public boolean isVelocidadeUtilizada() {
        return velocidadeUtilizada;
    }

    public void setVelocidadeUtilizada(boolean velocidadeUtilizada) {
        this.velocidadeUtilizada = velocidadeUtilizada;
    }
}
