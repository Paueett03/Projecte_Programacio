package com.mycompany.projecte_erp_hotel.model;

import java.sql.Date;
import java.time.LocalDate;

public class Persona {

    private int idPersona;
    private String email;
    private Date dataNaixement;
    private String nom;
    private String cognom;
    private String adreça;
    private String documentIdentitat;
    private String telefon;

    public Persona(String email, Date dataNaixement, String nom, String cognom, String adreça, String documentIdentitat, String telefon) {
        this.email = email;
        this.dataNaixement = dataNaixement;
        this.nom = nom;
        this.cognom = cognom;
        this.adreça = adreça;
        this.documentIdentitat = documentIdentitat;
        this.telefon = telefon;
    }


    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNaixement() {
        return dataNaixement;
    }

    public void setDataNaixement(Date dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

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
}
