package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Empleat {

    private String nom;
    private String cognom;
    private String adreça;
    private String documentIdentitat;
    private String telefon;
    private String email;
    private String dataNaixement;

    public Empleat(String nom, String cognom, String adreça, String documentIdentitat, String telefon, String email, String dataNaixement) {
        this.nom = nom;
        this.cognom = cognom;
        this.adreça = adreça;
        this.documentIdentitat = documentIdentitat;
        this.telefon = telefon;
        this.email = email;
        this.dataNaixement = dataNaixement;
    }

    // Getters y setters

    public void inserirEmpleat() throws SQLException {
        Connexio connexio = new Connexio();
        Connection conn = connexio.connecta();
        String sql = "INSERT INTO Empleats (nom, cognom, adreça, document_identitat, telefon, email, data_naixement) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.nom);
            stmt.setString(2, this.cognom);
            stmt.setString(3, this.adreça);
            stmt.setString(4, this.documentIdentitat);
            stmt.setString(5, this.telefon);
            stmt.setString(6, this.email);
            stmt.setString(7, this.dataNaixement);

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Nou empleat afegit correctament.");
            }
        } catch (SQLException e) {
            throw new SQLException("Error en inserir l'empleat: " + e.getMessage(), e);
        }
    }

    // Getters y setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getAdreça() {
        return adreça;
    }

    public void setAdreça(String adreça) {
        this.adreça = adreça;
    }

    public String getDocumentIdentitat() {
        return documentIdentitat;
    }

    public void setDocumentIdentitat(String documentIdentitat) {
        this.documentIdentitat = documentIdentitat;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataNaixement() {
        return dataNaixement;
    }

    public void setDataNaixement(String dataNaixement) {
        this.dataNaixement = dataNaixement;
    }
}
