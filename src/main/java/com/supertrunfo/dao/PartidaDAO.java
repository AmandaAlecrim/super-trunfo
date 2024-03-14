package com.supertrunfo.dao;

import com.supertrunfo.model.Partida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {
    private static Connection conexao;

    private static final String URL = "jdbc:postgresql://localhost:5432/super_trunfo";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public void inserirPartida(Partida partida) throws SQLException {
        this.conexao = obterConexao();

        String sql = "INSERT INTO partida (rounds_vencidos_jogador, rounds_vencidos_cpu, rounds_empatados, resultado, data, forca_utilizada, inteligencia_utilizada, velocidade_utilidade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setInt(1, partida.getRoundsVencidosJogador());
        ps.setInt(2, partida.getRoundsVencidosCPU());
        ps.setInt(3, partida.getRoundsEmpatados());
        ps.setString(4, partida.getResultado());
        ps.setDate(5, new java.sql.Date(partida.getData().getTime()));
        ps.setBoolean(6, partida.isForcaUtilizada());
        ps.setBoolean(7, partida.isInteligenciaUtilizada());
        ps.setBoolean(8, partida.isVelocidadeUtilizada());
        ps.executeUpdate();
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

        // Consulta para obter o último ID inserido
        String sqlUltimoId = "SELECT MAX(id) AS ultimo_id FROM partida";
        Statement statement = conexao.createStatement();
        ResultSet rs = statement.executeQuery(sqlUltimoId);
        int ultimoId = 0;
        if (rs.next()) {
            ultimoId = rs.getInt("ultimo_id");
        }
        rs.close();
        statement.close();

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
        ps.setInt(9, ultimoId);
        ps.executeUpdate();
        ps.close();
    }
}

