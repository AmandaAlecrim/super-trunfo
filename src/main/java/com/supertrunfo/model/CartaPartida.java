package com.supertrunfo.model;

public class CartaPartida {
    private int id;
    private boolean doJogador;
    private boolean utilizada;
    private Carta carta;
    private Partida partida;

    public CartaPartida() {
        this.carta = new Carta();
        this.partida = new Partida();
        this.doJogador = false;
        this.utilizada = false;
    }

    public CartaPartida(int id, boolean doJogador, boolean utilizada, Carta carta, Partida partida) {
        this.id = id;
        this.doJogador = doJogador;
        this.utilizada = utilizada;
        this.carta = carta;
        this.partida = partida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDoJogador() {
        return doJogador;
    }

    public void setDoJogador(boolean doJogador) {
        this.doJogador = doJogador;
    }

    public boolean isUtilizada() {
        return utilizada;
    }

    public void setUtilizada(boolean utilizada) {
        this.utilizada = utilizada;
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }
}
