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
public abstract class Persona {

    private int id_persona;
    private String email;
    private Date data_naixement;
    private String nom;
    private String cognom;
    private String adreça;
    private String document_identitat;
    private int telefon;

    public Persona(int id_persona, String email, Date data_naixement, String nom, String cognom, String adreça, String document_identitat, int telefon) {
        this.id_persona = id_persona;
        this.email = email;
        this.data_naixement = data_naixement;
        this.nom = nom;
        this.cognom = cognom;
        this.adreça = adreça;
        this.document_identitat = document_identitat;
        this.telefon = telefon;
    }

}
