package com.supertrunfo;

import com.supertrunfo.model.Carta;

import java.util.*;

public class Jogo {
    private List<Carta> baralho;
    private List<Carta> maoJogador;
    private List<Carta> maoCPU;
    private int rodadasVencidasJogador;
    private int rodadasVencidasCPU;
    private int rodadasEmpatadas;

    public Jogo(List<Carta> baralho) {
        this.baralho = baralho;
        this.maoJogador = new ArrayList<>();
        this.maoCPU = new ArrayList<>();
    }

    public void iniciarPartida() {
        // Distribuir cartas para o jogador e CPU
        for (int i = 0; i < 3; i++) {
            maoJogador.add(baralho.remove(0));
            maoCPU.add(baralho.remove(0));
        }

        // Iniciar rodadas
        for (int rodada = 1; rodada <= 3; rodada++) {
            System.out.println("Rodada " + rodada);
            jogarRodada();
        }

        // Verificar o vencedor da partida
        if (rodadasVencidasJogador > rodadasVencidasCPU) {
            System.out.println("Você venceu a partida!");
        } else if (rodadasVencidasJogador < rodadasVencidasCPU) {
            System.out.println("CPU venceu a partida!");
        } else {
            System.out.println("A partida terminou em empate!");
        }
    }

    private void jogarRodada() {
        // Escolher um atributo aleatório para jogar
        int atributo = escolherAtributo();
        System.out.println("Você escolheu o atributo: " + atributo);

        // Jogador joga uma carta
        Carta cartaJogador = escolherCarta(maoJogador);
        System.out.println("Jogador jogou a carta: " + cartaJogador.getNome());

        // CPU joga uma carta
        Random random = new Random();
        Carta cartaCPU = maoCPU.get(random.nextInt(maoCPU.size()));
        System.out.println("CPU jogou a carta: " + cartaCPU.getNome());

        // Comparar atributos
        int valorAtributoJogador = cartaJogador.getAtributo(atributo);
        int valorAtributoCPU = cartaCPU.getAtributo(atributo);
        if (valorAtributoJogador > valorAtributoCPU) {
            System.out.println("Você venceu a rodada!");
            rodadasVencidasJogador++;
        } else if (valorAtributoJogador < valorAtributoCPU) {
            System.out.println("CPU venceu a rodada!");
            rodadasVencidasCPU++;
        } else {
            System.out.println("Rodada empatada!");
            rodadasEmpatadas++;
        }
    }

    private int escolherAtributo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o atributo (0 para Força, 1 para Inteligência, 2 para Velocidade): ");
        int atributo = scanner.nextInt();
        return atributo;
    }

    private Carta escolherCarta(List<Carta> mao) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o número da carta que deseja jogar:");
        for (int i = 0; i < mao.size(); i++) {
            System.out.println((i + 1) + ". " + mao.get(i).getNome());
        }
        int escolha = scanner.nextInt();
        return mao.remove(escolha - 1);
    }
}

