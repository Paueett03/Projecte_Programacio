package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public Tasca(Date data_creacio, Date data_execucio, String descripcio) {
        this.dataCreacio = data_creacio;
        this.dataExecucio = data_execucio;
        this.descripcio = descripcio;
    }

    public Tasca() {
    }

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

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, this.dataCreacio);
            stmt.setDate(2, this.dataExecucio);
            stmt.setString(3, this.descripcio);
            stmt.setString(4, this.estat);
            stmt.executeUpdate();

            // Recuperar l'ID generat per la tasca
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.id_tasca = rs.getInt(1); // Assignem l'ID generat a l'atribut
            }
        }
    }

    public void assignarTascaAEmpleat(int idEmpleat) throws SQLException {
        Connection conn = new Connexio().connecta();

        try {
            // Iniciar transacción
            conn.setAutoCommit(false);

            // Inserir la tasca a la taula Tasca
            String sqlTasca = "INSERT INTO Tasca (data_creacio, data_execucio, descripcio, estat) VALUES (?, ?, ?, ?)";

            try (PreparedStatement stmtTasca = conn.prepareStatement(sqlTasca, Statement.RETURN_GENERATED_KEYS)) {
                stmtTasca.setDate(1, this.dataCreacio);
                stmtTasca.setDate(2, this.dataExecucio);
                stmtTasca.setString(3, this.descripcio);
                stmtTasca.setString(4, this.estat);
                stmtTasca.executeUpdate();

                // Obtenir l'ID generat per la tasca
                ResultSet rs = stmtTasca.getGeneratedKeys();
                if (rs.next()) {
                    this.id_tasca = rs.getInt(1);
                }
            }

            // Assignar la tasca a l'empleat
            String sqlAssignacio = "INSERT INTO Assig_Empleat_Tasca (id_empleat, id_tasca) VALUES (?, ?)";

            try (PreparedStatement stmtAssignacio = conn.prepareStatement(sqlAssignacio)) {
                stmtAssignacio.setInt(1, idEmpleat);
                stmtAssignacio.setInt(2, this.id_tasca);
                stmtAssignacio.executeUpdate();
            }

            // Confirmar la transacció
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
