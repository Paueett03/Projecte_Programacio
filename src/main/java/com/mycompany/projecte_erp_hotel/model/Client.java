package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class Client extends Persona {

    private int id_client;
    private String data_registre;
    private String tipus_client;
    private String targeta_credit;

    // Constructor con parámetros
    public Client(String data_registre, String tipus_client, String targeta_credit, String email, LocalDate data_naixement, String nom, String cognom, String adreça, String document_identitat, String telefon) {
        super(email, data_naixement, nom, cognom, adreça, document_identitat, telefon);
        this.data_registre = data_registre;
        this.tipus_client = tipus_client;
        this.targeta_credit = targeta_credit;
        this.id_client = id_client;
    }

    public Client() {
    }

    // Getters y Setters
    public int getId_client() {
        return id_client;
    }

    public String getData_registre() {
        return data_registre;
    }

    public String getTipus_client() {
        return tipus_client;
    }

    public String getTargeta_credit() {
        return targeta_credit;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public void setData_registre(String data_registre) {
        this.data_registre = data_registre;
    }

    public void setTipus_client(String tipus_client) {
        this.tipus_client = tipus_client;
    }

    public void setTargeta_credit(String targeta_credit) {
        this.targeta_credit = targeta_credit;
    }
    
    

    // Guardar un cliente en la base de datos
    public void save() {
        String sql = "INSERT INTO Clients (data_registre, tipus_client, targeta_credit) VALUES (?, ?, ?)";
        try (Connection conn = new Connexio().connecta(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.data_registre);
            pstmt.setString(2, this.tipus_client);
            pstmt.setString(3, this.targeta_credit);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
