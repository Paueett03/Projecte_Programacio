/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projecte_erp_hotel.model;

import java.util.Date;

/**
 *
 * @author alumne
 */
public class Empleat extends Persona {

    private int id_empleat;
    private String llocFeina;
    private Date data_contratacio;
    private double salariBrut;

    private enum estat_laboral {
        Actiu,
        Baixa,
        Permis
    }

    public Empleat(int id_empleat, String llocFeina, Date data_contratacio, double salariBrut, int id_persona, String email, Date data_naixement, String nom, String cognom, String adreça, String document_identitat, int telefon) {
        super(id_persona, email, data_naixement, nom, cognom, adreça, document_identitat, telefon);
        this.id_empleat = id_empleat;
        this.llocFeina = llocFeina;
        this.data_contratacio = data_contratacio;
        this.salariBrut = salariBrut;
    }

}
