package com.supertrunfo;

import com.supertrunfo.dao.CartaPartidaDAO;
import com.supertrunfo.dao.PartidaDAO;
import com.supertrunfo.model.Carta;
import com.supertrunfo.model.CartaPartida;
import com.supertrunfo.model.Partida;

import javax.swing.JOptionPane;
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
            JOptionPane.showMessageDialog(null, "\nRodada " + rodada);
            jogarRodada();
        }

        // Verificar o vencedor da partida
        if (rodadasVencidasJogador > rodadasVencidasCPU) {
            partida.setResultado("Você venceu a partida!");
        } else if (rodadasVencidasJogador < rodadasVencidasCPU) {
            partida.setResultado("CPU venceu a partida!");
        } else {
            partida.setResultado("A partida terminou em empate!");
        }
        JOptionPane.showMessageDialog(null, partida.getResultado());
        partidaDAO.atualizarPartida(partida);
    }

    private void jogarRodada() throws SQLException, InterruptedException {

        // Mostrar cartas jogador
        String message = "\nSuas cartas:\n";
        for (int i = 0; i < maoJogador.size(); i++) {
            message += "\n" + maoJogador.get(i).getCarta().getNome() + "\nFOR:\t" + maoJogador.get(i).getCarta().getForca() + "\nINT:\t" + maoJogador.get(i).getCarta().getInteligencia() + "\nVEL:\t" + maoJogador.get(i).getCarta().getVelocidade() + "\n";
        }
        JOptionPane.showMessageDialog(null, message);

        // Escolher um atributo para jogar
        char atributo = escolherAtributo(partida);
        partidaDAO.atualizarPartida(partida);

        // Jogador e CPU jogam uma carta
        CartaPartida cartaJogador = escolherCarta(maoJogador, atributo);
        CartaPartida cartaCPU = escolheCartaCPU(maoCPU, atributo);

        // Mostrar cartas jogador e CPU
        String cartasJogadas = "Jogador jogou a carta:\n\n" +
                cartaJogador.getCarta().getNome() + "\n" +
                "FOR: " + cartaJogador.getCarta().getForca() + "\n" +
                "INT: " + cartaJogador.getCarta().getInteligencia() + "\n" +
                "VEL: " + cartaJogador.getCarta().getVelocidade() + "\n\n" +
                "CPU jogou a carta:\n\n" +
                cartaCPU.getCarta().getNome() + "\n" +
                "FOR: " + cartaCPU.getCarta().getForca() + "\n" +
                "INT: " + cartaCPU.getCarta().getInteligencia() + "\n" +
                "VEL: " + cartaCPU.getCarta().getVelocidade() + "\n";

        JOptionPane.showMessageDialog(null, cartasJogadas);

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
            JOptionPane.showMessageDialog(null, "Você venceu a rodada!\n");
            rodadasVencidasJogador++;
        } else if (valorAtributoJogador < valorAtributoCPU) {
            JOptionPane.showMessageDialog(null, "CPU venceu a rodada!\n");
            rodadasVencidasCPU++;
        } else {
            JOptionPane.showMessageDialog(null, "Rodada empatada!\n");
            rodadasEmpatadas++;
        }
        partida.setRoundsVencidosJogador(rodadasVencidasJogador);
        partida.setRoundsVencidosCPU(rodadasVencidasCPU);
        partida.setRoundsEmpatados(rodadasEmpatadas);
        partidaDAO.atualizarPartida(partida);
    }

    private char escolherAtributo(Partida partida) {
        String mensagem = "Escolha o atributo:\n";
        if (!partida.isForcaUtilizada()) {
            mensagem += "F: Força\n";
        }
        if (!partida.isInteligenciaUtilizada()) {
            mensagem += "I: Inteligência\n";
        }
        if (!partida.isVelocidadeUtilizada()) {
            mensagem += "V: Velocidade\n";
        }

        char opcao;
        String input = JOptionPane.showInputDialog(null, mensagem);

        // Verifica se o input é nulo ou vazio
        while (input == null || input.isEmpty() || input.length() != 1 || (opcao = input.charAt(0)) == 'F' && partida.isForcaUtilizada() ||
                (opcao == 'I' && partida.isInteligenciaUtilizada()) ||
                (opcao == 'V' && partida.isVelocidadeUtilizada()) ||
                (opcao != 'F' && opcao != 'I' && opcao != 'V')) {
            if (input == null) { // Se o usuário clicou em Cancelar ou fechou a janela
                JOptionPane.showMessageDialog(null, "Jogo encerrado.");
                System.exit(0); // Encerra o programa
            }
            JOptionPane.showMessageDialog(null, "Atributo já foi utilizado ou é inválido.");
            input = JOptionPane.showInputDialog(null, mensagem);
        }

        return opcao;

    }

    private CartaPartida escolherCarta(List<CartaPartida> mao, char atributo) {
        String message = "Escolha o número da carta que deseja jogar:\n\n";
        for (int i = 0; i < mao.size(); i++) {
            message += (i + 1) + ".\n" + mao.get(i).getCarta().getNome() + "\n" + cartaUmAtributo(mao.get(i).getCarta(), atributo) + "\n\n";
        }

        int escolha = 0;
        boolean inputValido = false;

        while (!inputValido) {
            String input = JOptionPane.showInputDialog(null, message);

            // Verifica se o input é nulo ou vazio
            if (input == null || input.isEmpty()) {
                if (input == null) { // Se o usuário clicou em Cancelar ou fechou a janela
                    JOptionPane.showMessageDialog(null, "Jogo encerrado.");
                    System.exit(0); // Encerra o programa
                }
                JOptionPane.showMessageDialog(null, "Nenhuma opção selecionada. Escolha novamente: ");
                continue; // Reinicia o loop para solicitar uma nova entrada
            }
            try {
                // Tenta converter a entrada em um número inteiro
                escolha = Integer.parseInt(input);
                if (escolha >= 1 && escolha <= mao.size()) {
                    inputValido = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Opção inválida. Escolha novamente: ");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Insira um número válido: ");
            }
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

    public String cartaUmAtributo(Carta carta, char atributo) {
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
}
