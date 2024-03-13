package com.supertrunfo.model;

public class Carta {
    private int id;
    private String nome;
    private int forca;
    private int inteligencia;
    private int velocidade;

    public Carta() {
        this.id = 0; // Valor padrão para o id
        this.nome = ""; // Valor padrão para o nome
        this.forca = 0; // Valor padrão para a força
        this.inteligencia = 0; // Valor padrão para a inteligência
        this.velocidade = 0; // Valor padrão para a velocidade
    }

    public Carta(int id, String nome, int forca, int inteligencia, int velocidade) {
        this.id = id;
        this.nome = nome;
        this.forca = forca;
        this.inteligencia = inteligencia;
        this.velocidade = velocidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public int getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(int inteligencia) {
        this.inteligencia = inteligencia;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public int getAtributo(int indice) {
        switch (indice) {
            case 0: return forca;
            case 1: return inteligencia;
            case 2: return velocidade;
            default: throw new IllegalArgumentException("Índice de atributo inválido: " + indice);
        }
    }
}
