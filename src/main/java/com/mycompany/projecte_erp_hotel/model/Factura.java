package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Factura {

    private int id_factura;
    private double base_imposable;
    private LocalDate data_emisio;
    private String metode_pagament;
    private double iva;
    private double total;
    private int id_reserva;
    
        public Factura(int id_reserva, double base_imposable, LocalDate data_emisio, String metode_pagament, double iva, double total) {
        this.base_imposable = base_imposable;
        this.data_emisio = data_emisio;
        this.metode_pagament = metode_pagament;
        this.iva = iva;
        this.total = total;
        this.id_reserva = id_reserva;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
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

    // Guardar la factura en la base de datos
    public void save() {
        String sql = "INSERT INTO Factura (id_reserva, base_imposable, data_emisio, metode_pagament, iva, total) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = new Connexio().connecta(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id_reserva);
            pstmt.setDouble(2, this.base_imposable);
            pstmt.setDate(3, java.sql.Date.valueOf(this.data_emisio));  // Convertir LocalDate a java.sql.Date
            pstmt.setString(4, this.metode_pagament);
            pstmt.setDouble(5, this.iva);
            pstmt.setDouble(6, this.total);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
