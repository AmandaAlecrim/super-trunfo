package com.supertrunfo.dao;

import com.supertrunfo.model.Partida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO extends BaseDAO{
    private static Connection conexao;

    public void inserirPartida(Partida partida) throws SQLException {
        this.conexao = obterConexao();

        buscarProximoValorSequence(partida);

        String sql = "INSERT INTO partida (rounds_vencidos_jogador, rounds_vencidos_cpu, rounds_empatados, resultado, data, forca_utilizada, inteligencia_utilizada, velocidade_utilidade, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setInt(1, partida.getRoundsVencidosJogador());
        ps.setInt(2, partida.getRoundsVencidosCPU());
        ps.setInt(3, partida.getRoundsEmpatados());
        ps.setString(4, partida.getResultado());
        ps.setDate(5, new java.sql.Date(partida.getData().getTime()));
        ps.setBoolean(6, partida.isForcaUtilizada());
        ps.setBoolean(7, partida.isInteligenciaUtilizada());
        ps.setBoolean(8, partida.isVelocidadeUtilizada());
        ps.setInt(9, partida.getId());
        ps.execute();
        ps.close();
    }

    public List<Partida> listarPartidas() throws SQLException {
        this.conexao = obterConexao();

        List<Partida> partidas = new ArrayList<>();
        String sql = "SELECT * FROM partida";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Partida partida = new Partida();
            partida.setId(rs.getInt("id"));
            partida.setRoundsVencidosJogador(rs.getInt("rounds_vencidos_jogador"));
            partida.setRoundsVencidosCPU(rs.getInt("rounds_vencidos_cpu"));
            partida.setRoundsEmpatados(rs.getInt("rounds_empatados"));
            partida.setResultado(rs.getString("resultado"));
            partida.setData(rs.getDate("data"));
            partida.setForcaUtilizada(rs.getBoolean("forca_utilizada"));
            partida.setInteligenciaUtilizada(rs.getBoolean("inteligencia_utilizada"));
            partida.setVelocidadeUtilizada(rs.getBoolean("velocidade_utilidade"));
            partidas.add(partida);
        }
        rs.close();
        ps.close();
        return partidas;
    }

    public void atualizarPartida(Partida partida) throws SQLException {
        this.conexao = obterConexao();

        String sql = "UPDATE partida SET rounds_vencidos_jogador = ?, rounds_vencidos_cpu = ?, rounds_empatados = ?, resultado = ?, data = ?, forca_utilizada = ?, inteligencia_utilizada = ?, velocidade_utilidade = ? WHERE id = ?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setInt(1, partida.getRoundsVencidosJogador());
        ps.setInt(2, partida.getRoundsVencidosCPU());
        ps.setInt(3, partida.getRoundsEmpatados());
        ps.setString(4, partida.getResultado());
        ps.setDate(5, new java.sql.Date(partida.getData().getTime()));
        ps.setBoolean(6, partida.isForcaUtilizada());
        ps.setBoolean(7, partida.isInteligenciaUtilizada());
        ps.setBoolean(8, partida.isVelocidadeUtilizada());
        ps.setInt(9, partida.getId());
        ps.executeUpdate();
        ps.close();
    }

    public void buscarProximoValorSequence(Partida partida) throws SQLException {
        String sqlid = "SELECT nextval('partida_id_seq')";
        PreparedStatement ps1 = conexao.prepareStatement(sqlid);
        ResultSet rs = ps1.executeQuery();
        if (rs.next()) {
            int id = rs.getInt(1);
            partida.setId(id);
        }
    }
}

