package com.supertrunfo.dao;

import com.supertrunfo.exceptions.ForcaExcedidaException;
import com.supertrunfo.exceptions.NomeDuplicadoException;
import com.supertrunfo.model.Carta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartaDAO extends BaseDAO{
    private static Connection conexao;

    public void inserirCarta(Carta carta) throws SQLException, ForcaExcedidaException, NomeDuplicadoException {

        this.conexao = obterConexao();

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
        this.conexao = obterConexao();

        String sql = "SELECT * FROM carta WHERE nome = ?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, nome);
        ResultSet rs = ps.executeQuery();
        boolean existe = rs.next();
        rs.close();
        ps.close();
        return existe;
    }

    public Carta buscarPorId(int id) throws SQLException {
        this.conexao = obterConexao();

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


    public void atualizarCarta(Carta carta) throws SQLException, ForcaExcedidaException {
        this.conexao = obterConexao();

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

    public void excluirCarta(int id) throws SQLException {
        this.conexao = obterConexao();

        String sql = "DELETE FROM carta WHERE id = ?";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
    }

    public List<Carta> obterTodasCartas() throws SQLException {
        this.conexao = obterConexao();
        List<Carta> todasCartas = new ArrayList<>();

        String sql = "SELECT * FROM carta";
        PreparedStatement ps = conexao.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Carta carta = new Carta(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("forca"),
                    rs.getInt("inteligencia"),
                    rs.getInt("velocidade")
            );
            todasCartas.add(carta);
        }
        rs.close();
        ps.close();
        return todasCartas;
    }

}