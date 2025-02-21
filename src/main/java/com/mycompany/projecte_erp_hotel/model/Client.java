package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Client extends Persona {

    private int idClient;
    private LocalDate dataRegistre;
    private String tipusClient;
    private String targetaCredit;

    // Constructor con parámetros
    public Client(LocalDate dataRegistre, String tipusClient, String targetaCredit, 
                  String email, Date dataNaixement, String nom, String cognom, 
                  String adreça, String documentIdentitat, String telefon) {
        super(email, dataNaixement, nom, cognom, adreça, documentIdentitat, telefon);
        this.dataRegistre = dataRegistre;
        this.tipusClient = tipusClient;
        this.targetaCredit = targetaCredit;
    }

    public Client() {}

    // Getters y Setters
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public LocalDate getDataRegistre() {
        return dataRegistre;
    }

    public void setDataRegistre(LocalDate dataRegistre) {
        this.dataRegistre = dataRegistre;
    }

    public String getTipusClient() {
        return tipusClient;
    }

    public void setTipusClient(String tipusClient) {
        this.tipusClient = tipusClient;
    }

    public String getTargetaCredit() {
        return targetaCredit;
    }

    public void setTargetaCredit(String targetaCredit) {
        this.targetaCredit = targetaCredit;
    }

    // Guardar un cliente en la base de datos
    public boolean save(Client nuevoCliente) {
        String sqlPersona = "INSERT INTO Persona (email, data_naixement, nom, cognom, adreça, document_identitat, telefon) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlClient = "INSERT INTO Client (id_persona, data_registre, tipus_client, targeta_credit) VALUES (?, ?, ?, ?)";

        try (Connection conn = new Connexio().connecta()) {
            conn.setAutoCommit(false);

            // Insertar en Persona
            int idPersona = -1;
            try (PreparedStatement pstmtPersona = conn.prepareStatement(sqlPersona, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmtPersona.setString(1, nuevoCliente.getEmail());
                pstmtPersona.setObject(2, nuevoCliente.getDataNaixement());
                pstmtPersona.setString(3, nuevoCliente.getNom());
                pstmtPersona.setString(4, nuevoCliente.getCognom());
                pstmtPersona.setString(5, nuevoCliente.getAdreça());
                pstmtPersona.setString(6, nuevoCliente.getDocumentIdentitat());
                pstmtPersona.setString(7, nuevoCliente.getTelefon());
                pstmtPersona.executeUpdate();

                try (ResultSet generatedKeys = pstmtPersona.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idPersona = generatedKeys.getInt(1);
                    }
                }
            }

            if (idPersona != -1) {
                try (PreparedStatement pstmtClient = conn.prepareStatement(sqlClient)) {
                    pstmtClient.setInt(1, idPersona);
                    pstmtClient.setObject(2, nuevoCliente.getDataRegistre());
                    pstmtClient.setString(3, nuevoCliente.getTipusClient());
                    pstmtClient.setString(4, nuevoCliente.getTargetaCredit());
                    pstmtClient.executeUpdate();
                }
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
