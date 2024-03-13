package com.supertrunfo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static final String URL = "jdbc:postgresql://postgres:postgres@localhost:5432/super_trunfo";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "postgres";


    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}

