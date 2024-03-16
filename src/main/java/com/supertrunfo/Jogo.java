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
    private PartidaDAO partidaDAO;
    private Partida partida;
    private CartaPartidaDAO cartaPartidaDAO;

    public Jogo(List<Carta> baralho, PartidaDAO partidaDAO) {
        this.baralho = baralho;
        this.partidaDAO = partidaDAO;
        this.partida = new Partida();
        this.cartaPartidaDAO = new CartaPartidaDAO();
    }

    public void iniciarPartida() throws SQLException, InterruptedException {
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
            divisoria(30, '-');
            System.out.println("\nRodada " + rodada);
            jogarRodada();
        }

        // Verificar o vencedor da partida
        System.out.print('*');
        divisoria(30, '-');
        System.out.println('*');
        if (rodadasVencidasJogador > rodadasVencidasCPU) {
            System.out.println("|    Você venceu a partida!    |");
            partida.setResultado("Você venceu a partida!");
        } else if (rodadasVencidasJogador < rodadasVencidasCPU) {
            System.out.println("|     CPU venceu a partida!    |");
            partida.setResultado("CPU venceu a partida!");
        } else {
            System.out.println("| A partida terminou em empate!|");
            partida.setResultado("A partida terminou em empate!");
        }
        System.out.print('*');
        divisoria(30, '-');
        System.out.print("*\n");
        partidaDAO.atualizarPartida(partida);
    }

    private void jogarRodada() throws SQLException, InterruptedException {

        // Mostrar cartas jogador
        System.out.println("\nSuas cartas:\n");
        for (int i = 0; i < maoJogador.size(); i++) {
            divisoria(20, '*');
            System.out.println("\n"+ maoJogador.get(i).getCarta().getNome() + "\nFOR:\t" + maoJogador.get(i).getCarta().getForca() + "\nINT:\t" + maoJogador.get(i).getCarta().getInteligencia() + "\nVEL:\t" + maoJogador.get(i).getCarta().getVelocidade());
            divisoria(20, '*');
            System.out.println("\n");
        }

        // Escolher um atributo para jogar
        char atributo = escolherAtributo(partida);
        partidaDAO.atualizarPartida(partida);

        // Jogador joga uma carta
        CartaPartida cartaJogador = escolherCarta(maoJogador, atributo);
        System.out.println("Jogador jogou a carta:");
        divisoria(22,'*');
        System.out.println("\n\t" + cartaJogador.getCarta().getNome() + "\nFOR:\t" + cartaJogador.getCarta().getForca() + "\nINT:\t" + cartaJogador.getCarta().getInteligencia() + "\nVEL:\t" + cartaJogador.getCarta().getVelocidade());
        divisoria(22,'*');
        System.out.println("\n");

        // CPU joga uma carta
        CartaPartida cartaCPU = escolheCartaCPU(maoCPU, atributo);
        System.out.println("CPU jogou a carta:");
        divisoria(22,'*');
        System.out.println("\n\t" + cartaCPU.getCarta().getNome() + "\nFOR:\t" + cartaCPU.getCarta().getForca() + "\nINT:\t" + cartaCPU.getCarta().getInteligencia() + "\nVEL:\t" + cartaCPU.getCarta().getVelocidade());
        divisoria(22,'*');
        System.out.println("\n");

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
            valorAtributoJogador = cartaJogador.getCarta().getInteligencia();
            cartaJogador.setUtilizada(true);
            cartaPartidaDAO.atualizarCartaPartida(cartaJogador);
            valorAtributoCPU = cartaCPU.getCarta().getInteligencia();
            cartaCPU.setUtilizada(true);
            cartaPartidaDAO.atualizarCartaPartida(cartaCPU);
            partida.setInteligenciaUtilizada(true);
        } else if (atributo == 'V') {
            valorAtributoJogador = cartaJogador.getCarta().getVelocidade();
            cartaJogador.setUtilizada(true);
            cartaPartidaDAO.atualizarCartaPartida(cartaJogador);
            valorAtributoCPU = cartaCPU.getCarta().getVelocidade();
            cartaCPU.setUtilizada(true);
            cartaPartidaDAO.atualizarCartaPartida(cartaCPU);
            partida.setVelocidadeUtilizada(true);
        }

        // Comparar atributos
        if (valorAtributoJogador > valorAtributoCPU) {
            System.out.println("Você venceu a rodada!\n");
            rodadasVencidasJogador++;
        } else if (valorAtributoJogador < valorAtributoCPU) {
            System.out.println("CPU venceu a rodada!\n");
            rodadasVencidasCPU++;
        } else {
            System.out.println("Rodada empatada!\n");
            rodadasEmpatadas++;
        }
        partida.setRoundsVencidosJogador(rodadasVencidasJogador);
        partida.setRoundsVencidosCPU(rodadasVencidasCPU);
        partida.setRoundsEmpatados(rodadasEmpatadas);
        partidaDAO.atualizarPartida(partida);
        Thread.sleep(3500);
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

        while ((opcao == 'F' && partida.isForcaUtilizada()) ||
                (opcao == 'I' && partida.isInteligenciaUtilizada()) ||
                (opcao == 'V' && partida.isVelocidadeUtilizada()) ||
                (opcao != 'F' && opcao != 'I' && opcao != 'V')) {
            System.out.println("Atributo já foi utilizado ou é inválido.");
            System.out.println("Re-insira o atributo:");
            opcao = scanner.next().charAt(0);
        }
        return opcao;
    }

    private CartaPartida escolherCarta(List<CartaPartida> mao, char atributo) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Escolha o número da carta que deseja jogar:");
        for (int i = 0; i < mao.size(); i++) {
            System.out.println((i + 1) + ".");
            System.out.print("\t");
            divisoria(22, '*');
            System.out.println("\n\t" + mao.get(i).getCarta().getNome() + "\n\t" + cartaUmAtributo(mao.get(i).getCarta(), atributo));
            System.out.print("\t");
            divisoria(22, '*');
            System.out.println("\n");
        }
        int escolha = scanner.nextInt();

        while (escolha < 1 || escolha > mao.size()) {
            System.out.println("Opção inválida. Escolha novamente: ");
            escolha = scanner.nextInt();
        }

        return mao.remove(escolha - 1);
    }

    private CartaPartida escolheCartaCPU(List<CartaPartida> mao, char atributo) {
        int indicemelhorCarta = 0;
        int atual = 0;
        int proxima = 0;
        if (atributo == 'F') {
            for (int i = 0; i < mao.size() - 1; i++) {
                atual = mao.get(i).getCarta().getForca();
                proxima = mao.get(i + 1).getCarta().getForca();
                if (proxima > atual) {
                    indicemelhorCarta = i + 1;
                }
            }
        } else if (atributo == 'I') {
            for (int i = 0; i < mao.size() - 1; i++) {
                atual = mao.get(i).getCarta().getInteligencia();
                proxima = mao.get(i + 1).getCarta().getInteligencia();
                if (proxima > atual) {
                    indicemelhorCarta = i + 1;
                }
            }
        } else if (atributo == 'V') {
            for (int i = 0; i < mao.size() - 1; i++) {
                atual = mao.get(i).getCarta().getVelocidade();
                proxima = mao.get(i + 1).getCarta().getVelocidade();
                if (proxima > atual) {
                    indicemelhorCarta = i + 1;
                }
            }
        }
        return mao.remove(indicemelhorCarta);
    }

    public String cartaUmAtributo (Carta carta, char atributo) {
        String resultado = "";
        if (atributo == 'F') {
            resultado = "FOR:\t" + carta.getForca();
        }
        if (atributo == 'I') {
            resultado = "INT:\t" + carta.getInteligencia();
        }
        if (atributo == 'V') {
            resultado = "VEL:\t" + carta.getVelocidade();
        }
        return resultado;
    }

    public  void divisoria (int limite, char caractere) {
        for (int i = 0; i < limite; i++) {
            System.out.print(caractere);
        }
    }
}

