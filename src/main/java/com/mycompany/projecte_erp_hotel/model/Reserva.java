package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reserva {

    private int id_reserva;
    private int id_persona; // Cambiamos id_client a id_persona
    private double preu_total_reserva;
    private LocalDate data_reserva;
    private LocalDate data_inici;
    private LocalDate data_fi;
    private TipusReserva tipus_reserva;
    private tipus_IVA tipusIva;
    private String tipus_Pensio;
    private Habitacio habitacio;

    public enum tipus_IVA {
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

    public Reserva(int id_persona, LocalDate data_reserva, LocalDate data_inici, LocalDate data_fi, TipusReserva tipus_reserva, tipus_IVA tipusIva, String tipus_Pensio, Habitacio habitacio, double Preu_Total) {
        this.id_persona = id_persona;
        this.data_reserva = data_reserva;
        this.data_inici = data_inici;
        this.data_fi = data_fi;
        this.tipus_reserva = tipus_reserva;
        this.tipusIva = tipusIva;
        this.tipus_Pensio = tipus_Pensio;
        this.habitacio = habitacio;
        this.preu_total_reserva = Preu_Total;
    }
    
    public enum TipusReserva {
        Simple,
        Doble,
        Suite
    }
            
    public void insertarReserva() throws SQLException {
        Connexio connexio = new Connexio();
        Connection conn = connexio.connecta();

        String sql = "INSERT INTO Reserva (id_client, id_habitacio, preu_total_reserva, data_reserva, data_inici, data_fi, tipus_reserva, Tipus_IVA) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id_persona); // Usamos id_persona que és el id_client
            stmt.setInt(2, habitacio.getId_habitacio());
            stmt.setDouble(3, preu_total_reserva);
            stmt.setDate(4, java.sql.Date.valueOf(data_reserva));
            stmt.setDate(5, java.sql.Date.valueOf(data_inici));
            stmt.setDate(6, java.sql.Date.valueOf(data_fi));
            stmt.setString(7, tipus_reserva.name());
            stmt.setInt(8, tipusIva.getValor());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Reserva generada correctament.");
            }
        } catch (SQLException e) {
            System.err.println("Error al generar la reserva: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al tancar la conexió: " + e.getMessage());
            }
        }
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public int getId_persona() {
        return id_persona;
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

    public TipusReserva getTipus_reserva() {
        return tipus_reserva;
    }

    public String getTipus_Pensio() {
        return tipus_Pensio;
    }

    public Habitacio getHabitacio() {
        return habitacio;
    }

    public tipus_IVA getTipusIva() {
        return tipusIva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
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

    public void setTipus_Pensio(String tipus_Pensio) {
        this.tipus_Pensio = tipus_Pensio;
    }

    public void setHabitacio(Habitacio habitacio) {
        this.habitacio = habitacio;
    }

    public void setTipus_reserva(TipusReserva tipus_reserva) {
        this.tipus_reserva = tipus_reserva;
    }

    public void setTipusIva(tipus_IVA tipusIva) {
        this.tipusIva = tipusIva;
    }
}
