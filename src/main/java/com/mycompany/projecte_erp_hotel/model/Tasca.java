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
public class Tasca {

    private int id_tasca;
    private Date data_creacio;
    private Date data_ejecucio;
    private String descripcio;
    private String estat;

    public Tasca(Date data_creacio, Date data_ejecucio, String descripcio, String estat) {
        this.id_tasca = id_tasca;
        this.data_creacio = data_creacio;
        this.data_ejecucio = data_ejecucio;
        this.descripcio = descripcio;
        this.estat = estat;
    }

    public int getId_tasca() {
        return id_tasca;
    }

    public Date getData_creacio() {
        return data_creacio;
    }

    public Date getData_ejecucio() {
        return data_ejecucio;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public String getEstat() {
        return estat;
    }

    public void setId_tasca(int id_tasca) {
        this.id_tasca = id_tasca;
    }

    public void setData_creacio(Date data_creacio) {
        this.data_creacio = data_creacio;
    }

    public void setData_ejecucio(Date data_ejecucio) {
        this.data_ejecucio = data_ejecucio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }
    
}
