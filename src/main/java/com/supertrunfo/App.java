package com.supertrunfo;

import com.supertrunfo.dao.CartaDAO;
import com.supertrunfo.model.Carta;
import com.supertrunfo.exceptions.ForcaExcedidaException;
import com.supertrunfo.exceptions.NomeDuplicadoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Connection conexao = null;
        try {
            CartaDAO cartaDAO = new CartaDAO();

            // Obter todas as cartas do banco de dados
            List<Carta> todasCartas = new ArrayList<>();
            try {
                todasCartas = cartaDAO.obterTodasCartas();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Embaralhar as cartas
            Collections.shuffle(todasCartas);

            // Selecionar 6 cartas aleatórias para formar o baralho
            List<Carta> baralho = new ArrayList<>();
            for (int i = 0; i < 6; i++) {
                baralho.add(todasCartas.get(i));
            }

            // iniciando partida
            Jogo jogo = new Jogo(baralho);
            jogo.iniciarPartida();

//            inserirNovaCarta(cartaDAO);
//            buscarCartaPorId(cartaDAO);
//            editarCarta(cartaDAO);
//            excluirCarta(cartaDAO);

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

    public static void inserirNovaCarta(CartaDAO cartaDAO) throws SQLException, ForcaExcedidaException, NomeDuplicadoException {
        Carta novaCarta = new Carta();
        novaCarta.setNome("Teste");
        novaCarta.setForca(5);
        novaCarta.setInteligencia(5);
        novaCarta.setVelocidade(5);
        cartaDAO.inserirCarta(novaCarta);
    }

    // Método para buscar uma carta por ID
    public static void buscarCartaPorId(CartaDAO cartaDAO) throws SQLException {
        Carta cartaRecuperada = cartaDAO.buscarPorId(1);
        System.out.println("Carta recuperada: " + cartaRecuperada);
    }

    // Método para editar uma carta
    public static void editarCarta(CartaDAO cartaDAO) throws SQLException, ForcaExcedidaException {
        Carta cartaRecuperada = cartaDAO.buscarPorId(1);
        cartaRecuperada.setNome("Nova Carta Teste");
        cartaDAO.atualizarCarta(cartaRecuperada);
    }

    // Método para excluir uma carta
    public static void excluirCarta(CartaDAO cartaDAO) throws SQLException {
        cartaDAO.excluirCarta(1);
    }
}
