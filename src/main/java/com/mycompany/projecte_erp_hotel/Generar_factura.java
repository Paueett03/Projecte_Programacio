package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Factura;
import com.mycompany.projecte_erp_hotel.model.Connexio;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Generar_factura {

    @FXML
    private TextField idReservaField; // Camp per introduir l'ID de la reserva
    
    @FXML
    private ComboBox<String> metodePagamentCombo; // ComboBox per seleccionar el mètode de pagament
    
    @FXML
    private Button generarFacturaButton; // Botó per generar la factura

    @FXML
    public void initialize() {
        try {
            // Afegir opcions al ComboBox de mètodes de pagament
            metodePagamentCombo.getItems().addAll("Targeta", "Efectiu", "Transferència bancària");
            
            // Configurar l'acció del botó per generar la factura
            generarFacturaButton.setOnAction(event -> generarFactura());
        } catch (Exception e) {
            System.out.println("Error en la inicialització del controlador");
        }
    }

    private void generarFactura() {
        try {
            int idReserva = Integer.parseInt(idReservaField.getText()); // Obtenir l'ID de la reserva
            String metodePagament = metodePagamentCombo.getValue(); // Obtenir el mètode de pagament seleccionat

            // Comprovar que hi ha un mètode de pagament seleccionat
            if (metodePagament == null || metodePagament.isEmpty()) {
                mostrarMissatge(Alert.AlertType.ERROR, "Error de pagament", "Selecciona un mètode de pagament.");
                return;
            }

            // Obtenir les dades de la reserva des de la base de dades
            ReservaDades reservaDades = obtenirDadesReserva(idReserva);
            if (reservaDades == null) {
                mostrarMissatge(Alert.AlertType.ERROR, "Error de reserva", "No s'ha trobat cap reserva amb aquest ID.");
                return;
            }

            // Calcular la base imposable, l'IVA i el total
            double baseImposable = reservaDades.getPreuTotal() / (1 + reservaDades.getIva() / 100);
            double iva = reservaDades.getIva();
            double total = reservaDades.getPreuTotal();

            // Crear l'objecte Factura i guardar-lo a la base de dades
            Factura factura = new Factura(baseImposable, LocalDate.now(), metodePagament, iva, total);
            factura.save();

            mostrarMissatge(Alert.AlertType.INFORMATION, "Factura generada", "La factura s'ha generat correctament.");
        } catch (NumberFormatException e) {
            mostrarMissatge(Alert.AlertType.ERROR, "Error de format", "Assegura't que l'ID de la reserva sigui un número vàlid.");
        } catch (SQLException e) {
            mostrarMissatge(Alert.AlertType.ERROR, "Error de base de dades", "No s'ha pogut generar la factura: " + e.getMessage());
        }
    }

    private ReservaDades obtenirDadesReserva(int idReserva) throws SQLException {
        // Connexió a la base de dades i consulta per obtenir les dades de la reserva
        try (Connection conn = new Connexio().connecta();
             PreparedStatement stmt = conn.prepareStatement("SELECT preu_total_reserva, tipus_IVA FROM Reserva WHERE id_reserva = ?")) {
            stmt.setInt(1, idReserva);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double preuTotal = rs.getDouble("preu_total_reserva");
                    double iva = Double.parseDouble(rs.getString("tipus_IVA").replace("%", ""));
                    return new ReservaDades(preuTotal, iva);
                }
            }
        }
        return null;
    }

    private void mostrarMissatge(Alert.AlertType tipus, String titol, String missatge) {
        // Mostrar una alerta amb el tipus, títol i missatge especificats
        Alert alert = new Alert(tipus);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }

    // Classe interna per emmagatzemar les dades de la reserva
    private static class ReservaDades {
        private final double preuTotal;
        private final double iva;

        public ReservaDades(double preuTotal, double iva) {
            this.preuTotal = preuTotal;
            this.iva = iva;
        }

        public double getPreuTotal() {
            return preuTotal;
        }

        public double getIva() {
            return iva;
        }
    }
}
