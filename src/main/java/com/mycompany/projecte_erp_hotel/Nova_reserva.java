package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Reserva;
import com.mycompany.projecte_erp_hotel.model.Connexio;
import com.mycompany.projecte_erp_hotel.model.Habitacio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    @FXML
    private ComboBox<String> comboPensio;

    // Mapas para la conversión entre texto mostrado y valores de enums
    private Map<String, Reserva.TipusReserva> mapaTipusReserva = new HashMap<>();
    private Map<String, Reserva.tipus_IVA> mapaTipusIVA = new HashMap<>();
    
    // Mapa para los precios base por tipo de pensión y tipo de habitación
    private Map<String, Map<String, Double>> mapaPreciosPension = new HashMap<>();
    
    // Mapa para los valores de IVA
    private Map<String, Double> mapaValoresIVA = new HashMap<>();

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
            
            // Configuramos los valores de IVA
            mapaValoresIVA.put("0%", 0.0);
            mapaValoresIVA.put("7%", 0.07);
            mapaValoresIVA.put("21%", 0.21);
            
            // Configuramos los precios base para cada tipo de pensión y habitación
            Map<String, Double> preciosAD = new HashMap<>();
            preciosAD.put("Simple", 30.0);
            preciosAD.put("Doble", 50.0);
            preciosAD.put("Suite", 120.0);
            mapaPreciosPension.put("AD", preciosAD);
            
            Map<String, Double> preciosMP = new HashMap<>();
            preciosMP.put("Simple", 50.0);
            preciosMP.put("Doble", 70.0);
            preciosMP.put("Suite", 150.0);
            mapaPreciosPension.put("MP", preciosMP);

            // Ahora añado los valores de texto que el usuario verá
            tipusReservaCombo.getItems().addAll("Simple", "Doble", "Suite");
            tipusIVACombo.getItems().addAll("0%", "7%", "21%");

            // Establecemos valores por defecto para evitar que sean nulos
            tipusReservaCombo.setValue("Simple");
            tipusIVACombo.setValue("21%");
            comboPensio.setValue("AD");

            dataReservaPicker.setValue(LocalDate.now());
            
            // Añadimos listeners para calcular el precio automáticamente
            dataIniciPicker.valueProperty().addListener((obs, oldVal, newVal) -> calcularPrecioTotal());
            dataFiPicker.valueProperty().addListener((obs, oldVal, newVal) -> calcularPrecioTotal());
            tipusReservaCombo.valueProperty().addListener((obs, oldVal, newVal) -> calcularPrecioTotal());
            tipusIVACombo.valueProperty().addListener((obs, oldVal, newVal) -> calcularPrecioTotal());
            comboPensio.valueProperty().addListener((obs, oldVal, newVal) -> calcularPrecioTotal());

        } catch (Exception e) {
            System.out.println("Error en inicializació: " + e.getMessage());
            e.printStackTrace();
        }

        comboPensio.getItems().addAll("AD", "MP");
    }
    
    private void calcularPrecioTotal() {
        try {
            // Verificamos que todos los campos necesarios estén rellenados
            if (dataIniciPicker.getValue() == null || 
                dataFiPicker.getValue() == null || 
                tipusReservaCombo.getValue() == null || 
                tipusIVACombo.getValue() == null || 
                comboPensio.getValue() == null) {
                return;
            }
            
            // Obtenemos los valores seleccionados
            String tipusReserva = tipusReservaCombo.getValue();
            String pensio = comboPensio.getValue();
            String tipusIVA = tipusIVACombo.getValue();
            LocalDate dataInici = dataIniciPicker.getValue();
            LocalDate dataFi = dataFiPicker.getValue();
            
            // Calculamos el número de noches
            long numNoches = ChronoUnit.DAYS.between(dataInici, dataFi);
            
            if (numNoches <= 0) {
                preuTotalField.setText("0.0");
                return;
            }
            
            // Obtenemos el precio base por noche según tipo de pensión y habitación
            double precioBaseNoche = mapaPreciosPension.get(pensio).get(tipusReserva);
            
            // Calculamos el precio total sin IVA
            double precioSinIVA = precioBaseNoche * numNoches;
            
            // Añadimos el IVA
            double ivaValue = mapaValoresIVA.get(tipusIVA);
            double precioConIVA = precioSinIVA * (1 + ivaValue);
            
            // Formateamos y mostramos el precio total
            preuTotalField.setText(String.format("%.2f", precioConIVA).replace(",", "."));
            
        } catch (Exception e) {
            System.out.println("Error al calcular precio: " + e.getMessage());
            e.printStackTrace();
            preuTotalField.setText("0.0");
        }
    }

    @FXML
    private void guardarReserva() {
        try {
            // Validamos que todos los campos necesarios estén rellenados
            if (dniClientField.getText().isEmpty()
                    || dataReservaPicker.getValue() == null || dataIniciPicker.getValue() == null
                    || dataFiPicker.getValue() == null || tipusReservaCombo.getValue() == null
                    || tipusIVACombo.getValue() == null) {

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
                System.out.println("No inserada en la base de dades");
            }

            mostrarMissatge(Alert.AlertType.INFORMATION, "Reserva creada",
                    "La reserva s'ha guardat correctament.");

            // Limpiar los campos después de guardar
            limpiarFormulario();

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
        tipusReservaCombo.setValue("Habitació");
        tipusIVACombo.setValue("21%");
    }

    private int obtenirIdPersonaPerDNI(String dni) throws SQLException {
        try (Connection conn = new Connexio().connecta(); PreparedStatement stmt = conn.prepareStatement("SELECT id_persona FROM Persona WHERE document_identitat = ?")) {
            stmt.setString(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_persona");
                }
            }
        }
        return -1;
    }

    private Habitacio habitacioSeleccionada;

    @FXML
    private void calcularPreu() {
        if (habitacioSeleccionada != null && dataIniciPicker.getValue() != null && dataFiPicker.getValue() != null && comboPensio.getValue() != null) {
            Reserva reserva = new Reserva(0, dataIniciPicker.getValue(), dataFiPicker.getValue(), comboPensio.getValue(), habitacioSeleccionada);
            preuTotalField.setText("Preu Total: " + reserva.getPreu_total_reserva());
        }
    }

    private void mostrarMissatge(Alert.AlertType tipus, String titol, String missatge) {
        Alert alert = new Alert(tipus);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }
    
    public double convertirPreuTotalANum(){
        double preuTotalNum;
        preuTotalNum = Integer.parseInt(preuTotalField.getText());
        
        return preuTotalNum;
    }
}