package com.supertrunfo;

import com.supertrunfo.dao.CartaDAO;
import com.supertrunfo.dao.PartidaDAO;
import com.supertrunfo.model.Carta;
import com.supertrunfo.exceptions.ForcaExcedidaException;
import com.supertrunfo.exceptions.NomeDuplicadoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Connection conexao = null;
        try {
            CartaDAO cartaDAO = new CartaDAO();
            PartidaDAO partidaDAO = new PartidaDAO();
            Scanner scanner = new Scanner(System.in);
            char opcao = 'O';

            // Obter todas as cartas do banco de dados
            List<Carta> todasCartas =  cartaDAO.obterTodasCartas();

            novoJogo(todasCartas,  partidaDAO);

            while (opcao != 'N') {
                System.out.println("\nDeseja jogar outra partida?\nSim: S\nNão: N");
                opcao = scanner.next().charAt(0);
                if (opcao == 'S') {
                    novoJogo(todasCartas,  partidaDAO);
                } else if (opcao != 'N') {
                    System.out.println("Opção inválida.");
                }
            }
            if (opcao == 'N') {
                System.exit(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void novoJogo(List<Carta> todasCartas, PartidaDAO partidaDAO) throws SQLException, InterruptedException {
        // Embaralha as cartas
        Collections.shuffle(todasCartas);

        // Seleciona 6 cartas aleatórias para formar o baralho
        List<Carta> baralho = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            baralho.add(todasCartas.get(i));
        }

        // Inicia partida
        Jogo jogo = new Jogo(baralho, partidaDAO);
        jogo.iniciarPartida();
    }
}
