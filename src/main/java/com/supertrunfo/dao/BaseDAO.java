package com.supertrunfo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/super_trunfo";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";

    protected Connection conexao;

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
