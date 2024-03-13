package com.supertrunfo;

import com.supertrunfo.dao.CartaDAO;
import com.supertrunfo.model.Carta;
import com.supertrunfo.exceptions.ForcaExcedidaException;
import com.supertrunfo.exceptions.NomeDuplicadoException;
import com.supertrunfo.util.ConexaoBanco;

import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        Connection conexao = null;
        try {
            conexao = ConexaoBanco.obterConexao();

            CartaDAO cartaDAO = new CartaDAO();

            // Teste inserção carta
            Carta novaCarta = new Carta();
            novaCarta.setNome("Carta Teste");
            novaCarta.setForca(5);
            novaCarta.setInteligencia(5);
            novaCarta.setVelocidade(5);
            cartaDAO.inserirCarta(conexao, novaCarta);

            // Teste busca carta
            Carta cartaRecuperada = cartaDAO.buscarPorId(conexao, 1);
            System.out.println("Carta recuperada: " + cartaRecuperada);

            // Teste editar carta
            cartaRecuperada.setNome("Nova Carta Teste");
            cartaDAO.atualizarCarta(conexao, cartaRecuperada);

            // Teste exlcuir carta
            cartaDAO.excluirCarta(conexao, 1);

        } catch (SQLException | ForcaExcedidaException | NomeDuplicadoException e) {
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
}
