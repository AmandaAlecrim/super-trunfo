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
        char atributo = escolherAtributo(partida);
        partidaDAO.atualizarPartida(partida);

        // Jogador joga uma carta
        CartaPartida cartaJogador = escolherCarta(maoJogador);
        System.out.println("Jogador jogou a carta:\n    " + cartaJogador.getCarta().getNome() + " |força:" + cartaJogador.getCarta().getForca() + " |inteligência: " + cartaJogador.getCarta().getInteligencia() + " |velocidade: " + cartaJogador.getCarta().getVelocidade() + "|");

        // CPU joga uma carta
        Random random = new Random();
        int indiceCartaCPU = random.nextInt(maoCPU.size());
        CartaPartida cartaCPU = maoCPU.remove(indiceCartaCPU);
        System.out.println("CPU jogou a carta:\n    " + cartaCPU.getCarta().getNome() + " |força:" + cartaCPU.getCarta().getForca() + " |inteligência: " + cartaCPU.getCarta().getInteligencia() + " |velocidade: " + cartaCPU.getCarta().getVelocidade() + "|");

        int valorAtributoJogador = 0;
        int valorAtributoCPU = 0;

        // Verificando o caractere de atributo
        if (atributo == 'F') {
            valorAtributoJogador = cartaJogador.getCarta().getForca();
            cartaJogador.setUtilizada(true);
            cartaPartidaDAO.atualizarCartaPartida(cartaJogador);
            valorAtributoCPU = cartaCPU.getCarta().getForca();
            cartaCPU.setUtilizada(true);
            cartaPartidaDAO.atualizarCartaPartida(cartaCPU);
            partida.setForcaUtilizada(true);
        } else if (atributo == 'I') {
            valorAtributoJogador = cartaJogador.getCarta().getForca();
            cartaJogador.setUtilizada(true);
            cartaPartidaDAO.atualizarCartaPartida(cartaJogador);
            valorAtributoCPU = cartaCPU.getCarta().getForca();
            cartaCPU.setUtilizada(true);
            cartaPartidaDAO.atualizarCartaPartida(cartaCPU);
            partida.setInteligenciaUtilizada(true);
        } else if (atributo == 'V') {
            valorAtributoJogador = cartaJogador.getCarta().getForca();
            cartaJogador.setUtilizada(true);
            cartaPartidaDAO.atualizarCartaPartida(cartaJogador);
            valorAtributoCPU = cartaCPU.getCarta().getForca();
            cartaCPU.setUtilizada(true);
            cartaPartidaDAO.atualizarCartaPartida(cartaCPU);
            partida.setVelocidadeUtilizada(true);
        }

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

    private char escolherAtributo(Partida partida) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o atributo: ");
        if (!partida.isForcaUtilizada()) {
            System.out.println("F: Força");
        }
        if (!partida.isInteligenciaUtilizada()) {
            System.out.println("I: Inteligência");
        }
        if (!partida.isVelocidadeUtilizada()) {
            System.out.println("V: Velocidade");
        }
        char opcao = scanner.next().charAt(0);

        while (opcao != 'F' && opcao != 'I' && opcao != 'V') {
            System.out.println("O caractere inserido não é válido.");
            opcao = scanner.next().charAt(0);
        }
        return opcao;
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

