package com.supertrunfo.dao;

import com.supertrunfo.exceptions.ForcaExcedidaException;
import com.supertrunfo.exceptions.NomeDuplicadoException;
import com.supertrunfo.model.Carta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartaDAO {
    private Connection conexao;


    public void inserirCarta(Connection conexao, Carta carta) throws SQLException, ForcaExcedidaException, NomeDuplicadoException {
        if (carta.getForca() + carta.getInteligencia() + carta.getVelocidade() > 15) {
            throw new ForcaExcedidaException("A soma de força, inteligência e velocidade não pode ser maior que 15.");
        }

        if (existeCarta(carta.getNome())) {
            throw new NomeDuplicadoException("Já existe uma carta com o nome '" + carta.getNome() + "'.");
        }

        String sql = "INSERT INTO carta (nome, forca, inteligencia, velocidade) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, carta.getNome());
        ps.setInt(2, carta.getForca());
        ps.setInt(3, carta.getInteligencia());
        ps.setInt(4, carta.getVelocidade());
        ps.executeUpdate();
        ps.close();
    }

    private boolean existeCarta(String nome) throws SQLException {
        String sql = "SELECT * FROM carta WHERE nome = ?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, nome);
        ResultSet rs = ps.executeQuery();
        boolean existe = rs.next();
        rs.close();
        ps.close();
        return existe;
    }

    public Carta buscarPorId(Connection conexao, int id) throws SQLException {
        String sql = "SELECT * FROM carta WHERE id = ?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Carta carta = null;
        if (rs.next()) {
            carta = new Carta();
            carta.setId(rs.getInt("id"));
            carta.setNome(rs.getString("nome"));
            carta.setForca(rs.getInt("forca"));
            carta.setInteligencia(rs.getInt("inteligencia"));
            carta.setVelocidade(rs.getInt("velocidade"));
        }
        rs.close();
        ps.close();
        return carta;
    }


    public void atualizarCarta(Connection conexao, Carta carta) throws SQLException, ForcaExcedidaException {
        if (carta.getForca() + carta.getInteligencia() + carta.getVelocidade() > 15) {
            throw new ForcaExcedidaException("A soma de força, inteligência e velocidade não pode ser maior que 15.");
        }

        String sql = "UPDATE carta SET nome = ?, forca = ?, inteligencia = ?, velocidade = ? WHERE id = ?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, carta.getNome());
        ps.setInt(2, carta.getForca());
        ps.setInt(3, carta.getInteligencia());
        ps.setInt(4, carta.getVelocidade());
        ps.setInt(5, carta.getId());
        ps.executeUpdate();
        ps.close();
    }

    public void excluirCarta(Connection conexao, int id) throws SQLException {
        String sql = "DELETE FROM carta WHERE id = ?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

}