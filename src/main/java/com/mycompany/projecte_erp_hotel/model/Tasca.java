package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tasca {

    private int id_tasca;
    private Date data_creacio;
    private Date data_execucio;
    private String descripcio;
    private String estat;

    public Tasca(Date data_creacio, Date data_execucio, String descripcio, String estat) {
        this.data_creacio = data_creacio;
        this.data_execucio = data_execucio;
        this.descripcio = descripcio;
        this.estat = estat;
    }

    // Getters i setters

    public void insertarTasca() throws SQLException {
        // Establim la connexió amb la base de dades
        Connexio connexio = new Connexio();
        Connection conn = connexio.connecta();
        
        // Sentència SQL per inserir les dades
        String sql = "INSERT INTO Tasca (data_creacio, data_execucio, descripcio, estat) "
                   + "VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establim els valors dels paràmetres de la consulta
            stmt.setDate(1, this.data_creacio);  // Utilitzem `this` per accedir a les variables de la classe
            stmt.setDate(2, this.data_execucio);
            stmt.setString(3, this.descripcio);
            stmt.setString(4, this.estat);

            // Executem la inserció
            int filesAfectades = stmt.executeUpdate();
            if (filesAfectades > 0) {
                System.out.println("Tasca inserida correctament.");
            }
        } catch (SQLException e) {
            System.err.println("Error a l'inserir la tasca: " + e.getMessage());
        } finally {
            // Tanquem la connexió
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al tancar la connexió: " + e.getMessage());
            }
        }
    }
}
