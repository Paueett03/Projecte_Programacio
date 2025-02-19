/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projecte_erp_hotel.model;

/**
 *
 * @author alumne
 */
public class Habitacio {
    private int id_habitacio;
    private int numero_habitacio;
    private enum tipus_hab {
        Simple,
        Doble,
        Suit
    }
    private int capacitat;
    private double preu_nit_AD;
    private double preu_nit_MP;
    private String descripcio;
    private enum estat_hab {
        Disponible,
        Ocupada,
        En_neteja
    }

    public Habitacio(int numero_habitacio, int capacitat, double preu_nit_AD, double preu_nit_MP, String descripcio) {
        this.id_habitacio = id_habitacio;
        this.numero_habitacio = numero_habitacio;
        this.capacitat = capacitat;
        this.preu_nit_AD = preu_nit_AD;
        this.preu_nit_MP = preu_nit_MP;
        this.descripcio = descripcio;
    }

    public int getId_habitacio() {
        return id_habitacio;
    }

    public int getNumero_habitacio() {
        return numero_habitacio;
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

    public void setId_habitacio(int id_habitacio) {
        this.id_habitacio = id_habitacio;
    }

    public void setNumero_habitacio(int numero_habitacio) {
        this.numero_habitacio = numero_habitacio;
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
    
    
    
}
