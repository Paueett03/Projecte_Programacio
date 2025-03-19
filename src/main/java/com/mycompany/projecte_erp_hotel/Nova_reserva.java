package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Reserva;
import com.mycompany.projecte_erp_hotel.model.Connexio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Nova_reserva {
    @FXML
    private TextField dniClientField;
    @FXML
    private TextField preuTotalField;
    @FXML
    private DatePicker dataReservaPicker;
    @FXML
    private DatePicker dataIniciPicker;
    @FXML
    private DatePicker dataFiPicker;
    @FXML
    private ComboBox<String> tipusReservaCombo;
    @FXML
    private ComboBox<String> tipusIVACombo;
    
    // Mapas para la conversión entre texto mostrado y valores de enumerados
    private Map<String, Reserva.TipusReserva> mapaTipusReserva = new HashMap<>();
    private Map<String, Reserva.tipus_IVA> mapaTipusIVA = new HashMap<>();
    
    @FXML
    public void initialize() {
        try {
            // Configuramos los mapas
            mapaTipusReserva.put("Simple", Reserva.TipusReserva.Simple);
            mapaTipusReserva.put("Doble", Reserva.TipusReserva.Doble);
            mapaTipusReserva.put("Suite", Reserva.TipusReserva.Suite);
            
            mapaTipusIVA.put("0%", Reserva.tipus_IVA.cero);
            mapaTipusIVA.put("7%", Reserva.tipus_IVA.set);
            mapaTipusIVA.put("21%", Reserva.tipus_IVA.vint_i_u);
            
            // Ahora añado los valores de texto que el usuario verá
            tipusReservaCombo.getItems().addAll("Simple", "Doble", "Suite");
            tipusIVACombo.getItems().addAll("0%", "7%", "21%");
            
            // Establecemos valores por defecto para evitar que sean nulos
            tipusReservaCombo.setValue("Simple");
            tipusIVACombo.setValue("21%");
            
            dataReservaPicker.setValue(LocalDate.now());
            
        } catch (Exception e) {
            System.out.println("Error en inicializació: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void guardarReserva() {
        try {
            // Validamos que todos los campos necesarios estén rellenados
            if (dniClientField.getText().isEmpty() || preuTotalField.getText().isEmpty() ||
                dataReservaPicker.getValue() == null || dataIniciPicker.getValue() == null ||
                dataFiPicker.getValue() == null || tipusReservaCombo.getValue() == null ||
                tipusIVACombo.getValue() == null) {
                
                mostrarMissatge(Alert.AlertType.ERROR, "Camps incomplets", 
                               "Si us plau, ompliu tots els camps obligatoris.");
                return;
            }
            
            String dniClient = dniClientField.getText();
            int idPersona = obtenirIdPersonaPerDNI(dniClient);
            
            if (idPersona == -1) {
                mostrarMissatge(Alert.AlertType.ERROR, "Error de client", 
                               "No s'ha trobat cap persona amb aquest DNI.");
                return;
            }
            
            double preuTotal = Double.parseDouble(preuTotalField.getText());
            LocalDate dataReserva = dataReservaPicker.getValue();
            LocalDate dataInici = dataIniciPicker.getValue();
            LocalDate dataFi = dataFiPicker.getValue();
            
            // Obtenemos los enumerados a través de los mapas
            Reserva.TipusReserva tipusReserva = mapaTipusReserva.get(tipusReservaCombo.getValue());
            Reserva.tipus_IVA tipusIVA = mapaTipusIVA.get(tipusIVACombo.getValue());
            
            // Creamos la nueva reserva
            Reserva novaReserva = new Reserva(preuTotal, dataReserva, dataInici, dataFi, tipusReserva, tipusIVA);
            novaReserva.setId_persona(idPersona);
            
            if (true) {
                novaReserva.insertarReserva();
            } else {
                System.out.println("No inserada en la base de datos");
            }

            
            mostrarMissatge(Alert.AlertType.INFORMATION, "Reserva creada", 
                           "La reserva s'ha guardat correctament.");
            
            // Limpiar los campos después de guardar
            limpiarFormulario();
            
        } catch (NumberFormatException e) {
            mostrarMissatge(Alert.AlertType.ERROR, "Error de format", 
                           "Assegura't que el preu total sigui un número vàlid.");
        } catch (IllegalArgumentException e) {
            mostrarMissatge(Alert.AlertType.ERROR, "Error d'opció", 
                           "Assegura't que el tipus de reserva i l'IVA siguin vàlids.");
            System.err.println("Error detallado: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            mostrarMissatge(Alert.AlertType.ERROR, "Error de base de dades", 
                           "No s'ha pogut guardar la reserva: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void limpiarFormulario() {
        dniClientField.clear();
        preuTotalField.clear();
        dataReservaPicker.setValue(null);
        dataIniciPicker.setValue(null);
        dataFiPicker.setValue(null);
        tipusReservaCombo.setValue("Habitación");
        tipusIVACombo.setValue("21%");
    }
    
    private int obtenirIdPersonaPerDNI(String dni) throws SQLException {
        try (Connection conn = new Connexio().connecta(); 
             PreparedStatement stmt = conn.prepareStatement("SELECT id_persona FROM Persona WHERE document_identitat = ?")) {
            stmt.setString(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_persona");
                }
            }
        }
        return -1;
    }
    
    
    
    
    private void mostrarMissatge(Alert.AlertType tipus, String titol, String missatge) {
        Alert alert = new Alert(tipus);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }
}