package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Habitacio {

    private int id_habitacio;
    private int numero_habitacio;
    
    // Enumeración para tipo de habitación
    public enum TipusHab {
        Simple,
        Doble,
        Suit
    }
    private TipusHab tipus_hab;
    
    private int capacitat;
    private double preu_nit_AD;  // Preu per nit en Allotjament i Dinar
    private double preu_nit_MP;  // Preu per nit en Mitja Pensió
    private String descripcio;

    // Enumeración para el estado de la habitación
    public enum EstatHab {
        Disponible,
        Ocupada,
        En_neteja
    }
    private EstatHab estat_hab;

    public Habitacio(int numero_habitacio, TipusHab tipus_hab, int capacitat, double preu_nit_AD, double preu_nit_MP, String descripcio) {
        this.numero_habitacio = numero_habitacio;
        this.tipus_hab = tipus_hab;
        this.capacitat = capacitat;
        this.preu_nit_AD = preu_nit_AD;
        this.preu_nit_MP = preu_nit_MP;
        this.descripcio = descripcio;
    }

    // Getters y setters
    public int getId_habitacio() {
        return id_habitacio;
    }

    public int getNumero_habitacio() {
        return numero_habitacio;
    }

    public TipusHab getTipus_hab() {
        return tipus_hab;
    }

    public int getCapacitat() {
        return capacitat;
    }

    public double getPreu_nit_AD() {
        return preu_nit_AD;
    }

    public double getPreu_nit_MP() {
        return preu_nit_MP;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public EstatHab getEstat_hab() {
        return estat_hab;
    }

    public void setId_habitacio(int id_habitacio) {
        this.id_habitacio = id_habitacio;
    }

    public void setNumero_habitacio(int numero_habitacio) {
        this.numero_habitacio = numero_habitacio;
    }

    public void setTipus_hab(TipusHab tipus_hab) {
        this.tipus_hab = tipus_hab;
    }

    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }

    public void setPreu_nit_AD(double preu_nit_AD) {
        this.preu_nit_AD = preu_nit_AD;
    }

    public void setPreu_nit_MP(double preu_nit_MP) {
        this.preu_nit_MP = preu_nit_MP;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setEstat_hab(EstatHab estat_hab) {
        this.estat_hab = estat_hab;
    }

    // Guardar la habitación en la base de datos
    public void save() {
        String sql = "INSERT INTO Habitacio (numero_habitacio, tipus_hab, capacitat, preu_nit_AD, preu_nit_MP, descripcio, estat_hab) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = new Connexio().connecta(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.numero_habitacio);
            pstmt.setString(2, this.tipus_hab.name());  // Almacenar como String el nombre del enum
            pstmt.setInt(3, this.capacitat);
            pstmt.setDouble(4, this.preu_nit_AD);
            pstmt.setDouble(5, this.preu_nit_MP);
            pstmt.setString(6, this.descripcio);
            pstmt.setString(7, this.estat_hab.name());  // Almacenar como String el nombre del enum
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
