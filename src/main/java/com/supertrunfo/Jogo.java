package com.supertrunfo;

import com.supertrunfo.dao.PartidaDAO;
import com.supertrunfo.model.Carta;
import com.supertrunfo.model.Partida;

import java.sql.SQLException;
import java.util.*;

public class Jogo {
    private List<Carta> baralho;
    private List<Carta> maoJogador;
    private List<Carta> maoCPU;
    private int rodadasVencidasJogador;
    private int rodadasVencidasCPU;
    private int rodadasEmpatadas;
    private List<String> atributosDisponiveis = new ArrayList<>();
    private PartidaDAO partidaDAO;
    private Partida partida;

    public Jogo(List<Carta> baralho, PartidaDAO partidaDAO) {
        this.baralho = baralho;
        this.maoJogador = new ArrayList<>();
        this.maoCPU = new ArrayList<>();
        this.partidaDAO = partidaDAO;
        this.partida = new Partida();
    }

    public void iniciarPartida() {
        atributosDisponiveis.add("Força");
        atributosDisponiveis.add("Inteligência");
        atributosDisponiveis.add("Velocidade");

        partida.setData(new Date());

        try {
            partidaDAO.inserirPartida(partida);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        partida.setRoundsVencidosJogador(rodadasVencidasJogador);
        partida.setRoundsVencidosCPU(rodadasVencidasCPU);
        partida.setRoundsEmpatados(rodadasEmpatadas);

        // Verificar o vencedor da partida
        if (rodadasVencidasJogador > rodadasVencidasCPU) {
            System.out.println("Você venceu a partida!");
        } else if (rodadasVencidasJogador < rodadasVencidasCPU) {
            System.out.println("CPU venceu a partida!");
        } else {
            System.out.println("A partida terminou em empate!");
        }
        partida.setResultado("Partida concluída.");
        try {
            partidaDAO.atualizarPartida(partida);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void jogarRodada() {
        // Escolher um atributo para jogar
        int indiceAtributo = escolherAtributo(partida.isForcaUtilizada(), partida.isInteligenciaUtilizada(), partida.isVelocidadeUtilizada());

        // Adicionar atributo a partida
        String atributo = atributosDisponiveis.get(indiceAtributo);
        if (atributo.equals("Força")) {
            partida.setForcaUtilizada(true);
        } else if (atributo.equals("Inteligência")) {
            partida.setInteligenciaUtilizada(true);
        } else if (atributo.equals("Velocidade")) {
            partida.setVelocidadeUtilizada(true);
        }
        try {
            partidaDAO.atualizarPartida(partida);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Você escolheu o atributo: " + atributo);
        atributosDisponiveis.remove(indiceAtributo);

        // Jogador joga uma carta
        Carta cartaJogador = escolherCarta(maoJogador);
        System.out.println("Jogador jogou a carta:\n    " + cartaJogador.getNome() + " |força:" + cartaJogador.getForca() + " |inteligência: " + cartaJogador.getInteligencia() + " |velocidade: " + cartaJogador.getVelocidade() + "|");

        // CPU joga uma carta
        Random random = new Random();
        Carta cartaCPU = maoCPU.get(random.nextInt(maoCPU.size()));
        System.out.println("CPU jogou a carta:\n    " + cartaCPU.getNome() + " |força:" + cartaCPU.getForca() + " |inteligência: " + cartaCPU.getInteligencia() + " |velocidade: " + cartaCPU.getVelocidade() + "|");

        // Comparar atributos
        int valorAtributoJogador = cartaJogador.getAtributo(indiceAtributo);
        int valorAtributoCPU = cartaCPU.getAtributo(indiceAtributo);
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

    private int escolherAtributo(boolean forcaUtilizada, boolean inteligenciaUtilizada, boolean velocidadeUtilizada) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o atributo: ");
        int opcao = 0;
        if (!forcaUtilizada) {
            System.out.println((++opcao) + ". Força");
        }
        if (!inteligenciaUtilizada) {
            System.out.println((++opcao) + ". Inteligência");
        }
        if (!velocidadeUtilizada) {
            System.out.println((++opcao) + ". Velocidade");
        }
        int atributo = scanner.nextInt();

        while (atributo < 1 || atributo > opcao) {
            System.out.println("Opção inválida. Escolha novamente: ");
            atributo = scanner.nextInt();
        }

        return atributo - 1;
    }

    private Carta escolherCarta(List<Carta> mao) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o número da carta que deseja jogar:");
        for (int i = 0; i < mao.size(); i++) {
            System.out.println((i + 1) + ". " + mao.get(i).getNome() + " |força:" + mao.get(i).getForca() + " |inteligência: " + mao.get(i).getInteligencia() + " |velocidade: " + mao.get(i).getVelocidade() + "|");
        }
        int escolha = scanner.nextInt();

        while (escolha < 1 || escolha > 3) {
            System.out.println("Opção inválida. Escolha novamente: ");
            escolha = scanner.nextInt();
        }

        return mao.remove(escolha - 1);
    }
}

