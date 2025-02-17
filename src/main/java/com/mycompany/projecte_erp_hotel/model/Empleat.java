/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author alumne
 */
public class Empleat extends Persona {

    private int id_empleat;
    private String llocFeina;
    private LocalDate data_contratacio;
    private double salariBrut;

    private enum estat_laboral {
        Actiu,
        Baixa,
        Permis
    }

    public Empleat(String llocFeina, LocalDate data_contratacio, double salariBrut, String email, LocalDate data_naixement, String nom, String cognom, String adreça, String document_identitat, String telefon) {
        super(email, data_naixement, nom, cognom, adreça, document_identitat, telefon);
        this.llocFeina = llocFeina;
        this.data_contratacio = data_contratacio;
        this.salariBrut = salariBrut;
    }
    
// // Guardar un empleat en la base de datos
//    public void save() {
//        String sql = "INSERT INTO Clients (data_registre, tipus_client, targeta_credit) VALUES (?, ?, ?)";
//        try (Connection conn = new Connexio().connecta(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, this.data_registre);
//            pstmt.setString(2, this.tipus_client);
//            pstmt.setString(3, this.targeta_credit);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public Empleat() {
    }
}
