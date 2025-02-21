package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {

    // Mètode per inserir un nou empleat
    public void afegirEmpleat(String nom, String cognom, String email, String telefon, String adreca, LocalDate dataNaixement) throws SQLException {
        // Connexió a la base de dades
        Connection conn = new Connexio().connecta();

        // Sentència SQL per inserir un nou empleat
        String sql = "INSERT INTO Empleat (nom, cognom, email, telefon, adreca, data_naixement) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establir els valors de la sentència SQL
            stmt.setString(1, nom);
            stmt.setString(2, cognom);
            stmt.setString(3, email);
            stmt.setString(4, telefon);
            stmt.setString(5, adreca);
            stmt.setDate(6, java.sql.Date.valueOf(dataNaixement));

            // Executar la inserció
            int filesAfectades = stmt.executeUpdate();
            if (filesAfectades > 0) {
                System.out.println("Empleat inserit correctament.");
            }
        } catch (SQLException e) {
            System.err.println("Error al inserir l'empleat: " + e.getMessage());
        } finally {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        }
    }
}
