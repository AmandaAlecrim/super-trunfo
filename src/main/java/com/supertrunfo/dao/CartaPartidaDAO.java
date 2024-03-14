package com.supertrunfo.dao;

import com.supertrunfo.model.Carta;
import com.supertrunfo.model.CartaPartida;
import com.supertrunfo.model.Partida;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartaPartidaDAO extends BaseDAO{

    public void inserirCartaPartida(CartaPartida cartaPartida) throws SQLException {
        this.conexao = obterConexao();

        buscarProximoValorSequence(cartaPartida);

        String query = "INSERT INTO carta_partida (id_partida, id_carta, do_jogador, utilizada, id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexao.prepareStatement(query)) {
            statement.setInt(1, cartaPartida.getPartida().getId());
            statement.setInt(2, cartaPartida.getCarta().getId());
            statement.setBoolean(3, cartaPartida.isDoJogador());
            statement.setBoolean(4, cartaPartida.isUtilizada());
            statement.setInt(5, cartaPartida.getId());
            statement.execute();
        }
    }

    public void atualizarCartaPartida(CartaPartida cartaPartida) throws SQLException {
        String query = "UPDATE carta_partida SET id_partida = ?, id_carta = ?, do_jogador = ?, utilizada = ? WHERE id = ?";
        try (PreparedStatement statement = conexao.prepareStatement(query)) {
            statement.setInt(1, cartaPartida.getPartida().getId());
            statement.setInt(2, cartaPartida.getCarta().getId());
            statement.setBoolean(3, cartaPartida.isDoJogador());
            statement.setBoolean(4, cartaPartida.isUtilizada());
            statement.setInt(5, cartaPartida.getId());
            statement.executeUpdate();
        }
    }

    public void buscarProximoValorSequence(CartaPartida cartaPartida) throws SQLException {
        String sqlid = "SELECT nextval('carta_partida_id_seq')";
        PreparedStatement ps1 = conexao.prepareStatement(sqlid);
        ResultSet rs = ps1.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            cartaPartida.setId(id);
        }
    }
}
