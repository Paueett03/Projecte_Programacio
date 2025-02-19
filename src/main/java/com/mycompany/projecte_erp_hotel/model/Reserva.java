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
public class Reserva {

    private int id_reserva;
    private double preu_total_reserva;
    private LocalDate data_reserva;
    private LocalDate data_inici;
    private LocalDate data_fi;
    private String tipus_reserva;

    private enum tipus_IVA {
        cero(0),
        set(7),
        vint_i_u(21);
        private final int value;

        private tipus_IVA(int value) {
            this.value = value;
        }

        public int getValor() {
            return value;
        }
    }

    public Reserva(double preu_total_reserva, LocalDate data_reserva, LocalDate data_inici, LocalDate data_fi, String tipus_reserva) {
        this.id_reserva = id_reserva;
        this.preu_total_reserva = preu_total_reserva;
        this.data_reserva = data_reserva;
        this.data_inici = data_inici;
        this.data_fi = data_fi;
        this.tipus_reserva = tipus_reserva;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public double getPreu_total_reserva() {
        return preu_total_reserva;
    }

    public LocalDate getData_reserva() {
        return data_reserva;
    }

    public LocalDate getData_inici() {
        return data_inici;
    }

    public LocalDate getData_fi() {
        return data_fi;
    }

    public String getTipus_reserva() {
        return tipus_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public void setPreu_total_reserva(double preu_total_reserva) {
        this.preu_total_reserva = preu_total_reserva;
    }

    public void setData_reserva(LocalDate data_reserva) {
        this.data_reserva = data_reserva;
    }

    public void setData_inici(LocalDate data_inici) {
        this.data_inici = data_inici;
    }

    public void setData_fi(LocalDate data_fi) {
        this.data_fi = data_fi;
    }

    public void setTipus_reserva(String tipus_reserva) {
        this.tipus_reserva = tipus_reserva;
    }

}
