/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projecte_erp_hotel.model;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author alumne
 */
public class Persona {

    private int id_persona;
    private String email;
    private LocalDate data_naixement;
    private String nom;
    private String cognom;
    private String adreça;
    private String document_identitat;
    private String telefon;

    public Persona(String email, LocalDate data_naixement, String nom, String cognom, String adreça, String document_identitat, String telefon) {
        this.email = email;
        this.data_naixement = data_naixement;
        this.nom = nom;
        this.cognom = cognom;
        this.adreça = adreça;
        this.document_identitat = document_identitat;
        this.telefon = telefon;
    }

    public Persona() {
    }
    
    public int getId_persona() {
        return id_persona;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getData_naixement() {
        return data_naixement;
    }

    public String getNom() {
        return nom;
    }

    public String getCognom() {
        return cognom;
    }

    public String getAdreça() {
        return adreça;
    }

    public String getDocument_identitat() {
        return document_identitat;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setData_naixement(LocalDate data_naixement) {
        this.data_naixement = data_naixement;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public void setAdreça(String adreça) {
        this.adreça = adreça;
    }

    public void setDocument_identitat(String document_identitat) {
        this.document_identitat = document_identitat;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

}
