/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projecte_erp_hotel.model;

import java.time.LocalDate;

/**
 *
 * @author alumne
 */
public class Factura {

    private int id_factura;
    private double base_imposable;
    private LocalDate data_emisio;
    private String metode_pagament;
    private double iva;
    private double total;

    public Factura(double base_imposable, LocalDate data_emisio, String metode_pagament, double iva, double total) {
        this.base_imposable = base_imposable;
        this.data_emisio = data_emisio;
        this.metode_pagament = metode_pagament;
        this.iva = iva;
        this.total = total;
        this.id_factura = id_factura;
    }

    public int getId_factura() {
        return id_factura;
    }

    public double getBase_imposable() {
        return base_imposable;
    }

    public LocalDate getData_emisio() {
        return data_emisio;
    }

    public String getMetode_pagament() {
        return metode_pagament;
    }

    public double getIva() {
        return iva;
    }

    public double getTotal() {
        return total;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public void setBase_imposable(double base_imposable) {
        this.base_imposable = base_imposable;
    }

    public void setData_emisio(LocalDate data_emisio) {
        this.data_emisio = data_emisio;
    }

    public void setMetode_pagament(String metode_pagament) {
        this.metode_pagament = metode_pagament;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    
}
