package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Reserva {

    private int id_reserva;
    private double preu_total_reserva;
    private LocalDate data_reserva;
    private LocalDate data_inici;
    private LocalDate data_fi;
    private TipusReserva tipus_reserva;
    private tipus_IVA tipusIva; // Campo para almacenar el tipo de IVA

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

    public Reserva(double preu_total_reserva, LocalDate data_reserva, LocalDate data_inici, LocalDate data_fi,
            TipusReserva tipus_reserva, tipus_IVA tipusIva) {
        this.preu_total_reserva = preu_total_reserva;
        this.data_reserva = data_reserva;
        this.data_inici = data_inici;
        this.data_fi = data_fi;
        this.tipus_reserva = tipus_reserva;
        this.tipusIva = tipusIva;
    }

    public enum TipusReserva {
        HABITACIO,
        PAQUETE,
        OTRO
    }

    public void insertarReserva() throws SQLException {
        // Establecemos la conexión con la base de datos
        Connexio connexio = new Connexio();
        Connection conn = connexio.connecta();

        // Sentencia SQL para insertar los datos
        String sql = "INSERT INTO Reserva (preu_total_reserva, data_reserva, data_inici, data_fi, tipus_reserva, tipus_iva) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecemos los valores de los parámetros de la consulta
            stmt.setDouble(1, preu_total_reserva);
            stmt.setDate(2, java.sql.Date.valueOf(data_reserva));
            stmt.setDate(3, java.sql.Date.valueOf(data_inici));
            stmt.setDate(4, java.sql.Date.valueOf(data_fi));
            stmt.setString(5, tipus_reserva.name());  // Convertir el enum a String
            stmt.setInt(6, tipusIva.getValor());  // Establecer el valor del tipo de IVA

            // Ejecutamos la inserción
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Reserva insertada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar la reserva: " + e.getMessage());
        } finally {
            // Cerramos la conexión
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

}
