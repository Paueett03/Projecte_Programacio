package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tasca {
    private int id_tasca;
    private Date dataCreacio;
    private Date dataExecucio;
    private String descripcio;
    private String estat;

    public Tasca(Date data_creacio, Date data_execucio, String descripcio, String estat) {
        this.dataCreacio = data_creacio;
        this.dataExecucio = data_execucio;
        this.descripcio = descripcio;
        this.estat = estat;
    }

    public Tasca() {}

    // Getters i setters

    public int getId_tasca() {
        return id_tasca;
    }

    public Date getData_creacio() {
        return dataCreacio;
    }

    public Date getData_execucio() {
        return dataExecucio;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public String getEstat() {
        return estat;
    }

    
    public void insertarTasca() throws SQLException {
        Connection conn = new Connexio().connecta();
        String sql = "INSERT INTO Tasca (data_creacio, data_execucio, descripcio, estat) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, this.dataCreacio);
            stmt.setDate(2, this.dataExecucio);
            stmt.setString(3, this.descripcio);
            stmt.setString(4, this.estat);
            stmt.executeUpdate();
        }
    }

    public static List<Tasca> obtenirTasques() throws SQLException {
        List<Tasca> tasques = new ArrayList<>();
        Connection conn = new Connexio().connecta();
        String sql = "SELECT * FROM Tasca";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Tasca tasca = new Tasca(
                    rs.getDate("data_creacio"),
                    rs.getDate("data_execucio"),
                    rs.getString("descripcio"),
                    rs.getString("estat")
                );
                tasques.add(tasca);
            }
        }
        return tasques;
    }
}