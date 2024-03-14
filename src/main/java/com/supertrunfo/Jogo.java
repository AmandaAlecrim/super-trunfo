package com.supertrunfo;

import com.supertrunfo.dao.CartaPartidaDAO;
import com.supertrunfo.dao.PartidaDAO;
import com.supertrunfo.model.Carta;
import com.supertrunfo.model.CartaPartida;
import com.supertrunfo.model.Partida;

import java.sql.SQLException;
import java.util.*;

public class Jogo {
    private List<Carta> baralho;
    private List<CartaPartida> maoJogador = new ArrayList<>();
    private List<CartaPartida> maoCPU = new ArrayList<>();
    private int rodadasVencidasJogador;
    private int rodadasVencidasCPU;
    private int rodadasEmpatadas;
    private List<String> atributosDisponiveis = new ArrayList<>();
    private PartidaDAO partidaDAO;
    private Partida partida;
    private CartaPartidaDAO cartaPartidaDAO;

    public Jogo(List<Carta> baralho, PartidaDAO partidaDAO) {
        this.baralho = baralho;
        this.partidaDAO = partidaDAO;
        this.partida = new Partida();
        this.cartaPartidaDAO = new CartaPartidaDAO();
    }

    public void iniciarPartida() throws SQLException {
        atributosDisponiveis.add("Força");
        atributosDisponiveis.add("Inteligência");
        atributosDisponiveis.add("Velocidade");

        partida.setData(new Date());
        partidaDAO.inserirPartida(partida);

        // Distribuir cartas para o jogador e CPU
        for (int i = 0; i < 3; i++) {
            CartaPartida cartaPartida = new CartaPartida();
            Carta carta = baralho.get(0);
            cartaPartida.setCarta(carta);
            cartaPartida.setPartida(partida);
            cartaPartida.setDoJogador(true);
            maoJogador.add(cartaPartida);
            cartaPartidaDAO.inserirCartaPartida(cartaPartida);
            baralho.remove(0);

            cartaPartida = new CartaPartida();
            carta = baralho.get(0);
            cartaPartida.setCarta(carta);
            cartaPartida.setPartida(partida);
            cartaPartida.setDoJogador(false);
            maoCPU.add(cartaPartida);
            cartaPartidaDAO.inserirCartaPartida(cartaPartida);
            baralho.remove(0);
        }

        // Iniciar rodadas
        for (int rodada = 1; rodada <= 3; rodada++) {
            System.out.println("Rodada " + rodada);
            jogarRodada();
        }

        // Verificar o vencedor da partida
        if (rodadasVencidasJogador > rodadasVencidasCPU) {
            System.out.println("Você venceu a partida!");
            partida.setResultado("Você venceu a partida!");
        } else if (rodadasVencidasJogador < rodadasVencidasCPU) {
            System.out.println("CPU venceu a partida!");
            partida.setResultado("CPU venceu a partida!");
        } else {
            System.out.println("A partida terminou em empate!");
            partida.setResultado("A partida terminou em empate!");
        }
        partidaDAO.atualizarPartida(partida);
    }

    private void jogarRodada() throws SQLException {
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
        partidaDAO.atualizarPartida(partida);

        System.out.println("Você escolheu o atributo: " + atributo);
        atributosDisponiveis.remove(indiceAtributo);

        // Jogador joga uma carta
        CartaPartida cartaJogador = escolherCarta(maoJogador);
        System.out.println("Jogador jogou a carta:\n    " + cartaJogador.getCarta().getNome() + " |força:" + cartaJogador.getCarta().getForca() + " |inteligência: " + cartaJogador.getCarta().getInteligencia() + " |velocidade: " + cartaJogador.getCarta().getVelocidade() + "|");

        // CPU joga uma carta
        Random random = new Random();
        int indiceCartaCPU = random.nextInt(maoCPU.size());
        CartaPartida cartaCPU = maoCPU.remove(indiceCartaCPU);
        System.out.println("CPU jogou a carta:\n    " + cartaCPU.getCarta().getNome() + " |força:" + cartaCPU.getCarta().getForca() + " |inteligência: " + cartaCPU.getCarta().getInteligencia() + " |velocidade: " + cartaCPU.getCarta().getVelocidade() + "|");


        // Capturar e atualizar atributos
        int valorAtributoJogador = cartaJogador.getCarta().getAtributo(indiceAtributo);
        cartaJogador.setUtilizada(true);
        cartaPartidaDAO.atualizarCartaPartida(cartaJogador);
        int valorAtributoCPU = cartaCPU.getCarta().getAtributo(indiceAtributo);
        cartaCPU.setUtilizada(true);
        cartaPartidaDAO.atualizarCartaPartida(cartaCPU);

        // Comparar atributos
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
        partida.setRoundsVencidosJogador(rodadasVencidasJogador);
        partida.setRoundsVencidosCPU(rodadasVencidasCPU);
        partida.setRoundsEmpatados(rodadasEmpatadas);
        partidaDAO.atualizarPartida(partida);
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

    private CartaPartida escolherCarta(List<CartaPartida> mao) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o número da carta que deseja jogar:");
        for (int i = 0; i < mao.size(); i++) {
            System.out.println((i + 1) + ". " + mao.get(i).getCarta().getNome() + " |força:" + mao.get(i).getCarta().getForca() + " |inteligência: " + mao.get(i).getCarta().getInteligencia() + " |velocidade: " + mao.get(i).getCarta().getVelocidade() + "|");
        }
        int escolha = scanner.nextInt();

        while (escolha < 1 || escolha > 3) {
            System.out.println("Opção inválida. Escolha novamente: ");
            escolha = scanner.nextInt();
        }

        return mao.remove(escolha - 1);
    }
}

