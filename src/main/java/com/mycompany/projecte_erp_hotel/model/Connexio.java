package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexio {

    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/ERP_Hotel";
    private final String USER = "usuaridam";
    private final String PASSWD = "usuaridam";

    public Connection connecta() throws SQLException {
        Connection connexio = null;
        try {
            // Cargar el driver de MySQL
            Class.forName(DRIVER); 
            // Establecer la conexión
            connexio = DriverManager.getConnection(URL, USER, PASSWD); 
        } catch (SQLException | ClassNotFoundException ex) {
            // Manejo de la excepción
            System.err.println("Error de conexión: " + ex.getMessage());
            throw new SQLException("Error al conectar a la base de datos.", ex); // Relanzar la excepción
        }
        return connexio;
    }
}
