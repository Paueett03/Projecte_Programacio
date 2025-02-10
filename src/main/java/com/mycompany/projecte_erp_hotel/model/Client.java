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
public class Client extends Persona {

    private int id_client;
    private Date data_registre;
    private String targeta_credit;

    private enum tipus_client {
        Ocasional,
        Regular,
        VIP
    }

    public Client(int id_client, Date data_registre, String targeta_credit, int id_persona, String email, Date data_naixement, String nom, String cognom, String adreça, String document_identitat, int telefon) {
        super(id_persona, email, data_naixement, nom, cognom, adreça, document_identitat, telefon);
        this.id_client = id_client;
        this.data_registre = data_registre;
        this.targeta_credit = targeta_credit;
    }

}
