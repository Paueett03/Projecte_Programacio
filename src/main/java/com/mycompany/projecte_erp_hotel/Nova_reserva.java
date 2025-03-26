package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Reserva;
import com.mycompany.projecte_erp_hotel.model.Connexio;
import com.mycompany.projecte_erp_hotel.model.Habitacio;
import com.mycompany.projecte_erp_hotel.model.Habitacio.TipusHab;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private ComboBox<String> tipusPensioCombo;
    @FXML
    private ComboBox<Habitacio> numHab;

    
    private final ObservableList<Habitacio> habitacions = FXCollections.observableArrayList();
    
    // Mapas para la conversión entre texto mostrado y valores de enums
    private final Map<String, Reserva.TipusReserva> mapaTipusReserva = new HashMap<>();
    private final Map<String, Reserva.tipus_IVA> mapaTipusIVA = new HashMap<>();
    
    // Mapa para los precios base por tipo de pensión y tipo de habitación
    private final Map<String, Map<String, Double>> mapaPreciosPension = new HashMap<>();
    
    // Mapa para los valores de IVA
    private final Map<String, Double> mapaValoresIVA = new HashMap<>();

    @FXML
    public void initialize() throws SQLException {
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
            tipusPensioCombo.setValue("AD");

            dataReservaPicker.setValue(LocalDate.now());
            
            // Añadimos listeners para calcular el precio automáticamente
            dataIniciPicker.valueProperty().addListener((obs, oldVal, newVal) -> calcularPrecioTotal());
            dataFiPicker.valueProperty().addListener((obs, oldVal, newVal) -> calcularPrecioTotal());
            tipusReservaCombo.valueProperty().addListener((obs, oldVal, newVal) -> calcularPrecioTotal());
            tipusIVACombo.valueProperty().addListener((obs, oldVal, newVal) -> calcularPrecioTotal());
            tipusPensioCombo.valueProperty().addListener((obs, oldVal, newVal) -> calcularPrecioTotal());

        } catch (Exception e) {
            System.out.println("Error en inicializació: " + e.getMessage());
            e.printStackTrace();
        }
        
    // Això farà que el ComboBox mostri el número d'habitació en lloc de l'objecte Habitacio
    numHab.setCellFactory(lv -> new ListCell<Habitacio>() {
        @Override
        protected void updateItem(Habitacio item, boolean empty) {
            super.updateItem(item, empty);
            setText((empty || item == null) ? null : item.getNumero_habitacio());
        }
    });

    numHab.setButtonCell(new ListCell<Habitacio>() {
        @Override
        protected void updateItem(Habitacio item, boolean empty) {
            super.updateItem(item, empty);
            setText((empty || item == null) ? null : item.getNumero_habitacio());
        }
    });

        tipusPensioCombo.getItems().addAll("AD", "MP");
        carregarHabitacions();
    }
    
    private void carregarHabitacions() throws SQLException {
    Connexio connexio = new Connexio();
    Connection conn = connexio.connecta();
    if (conn != null) {
        try {
            String query = "SELECT id_habitacio, numero_habitacio, tipus, capacitat, preu_nit_AP, preu_nit_MP, descripcio, estat FROM Habitacio";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            habitacions.clear(); // Netejar la llista abans d'afegir dades

            while (rs.next()) {
                int id = rs.getInt(1);
                String numeroHabitacio = String.valueOf(rs.getInt(2));
                TipusHab tipus_hab = TipusHab.valueOf(rs.getString(3));
                int capacitat = rs.getInt(4);
                double preuNitAP = rs.getDouble(5);
                double preuNitMP = rs.getDouble(6);
                String descripcio = rs.getString(7);
                String estat = rs.getString(8);
                
                
                // Crear un objecte Habitacio i afegir-lo a la llista
                Habitacio habitacio = new Habitacio(id, numeroHabitacio, tipus_hab, capacitat, preuNitAP, preuNitMP, descripcio, estat);
                habitacions.add(habitacio);
            }
            numHab.setItems(habitacions); // Assignar la llista al ComboBox
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }
}
    
    private void calcularPrecioTotal() {
        try {
            // Verificamos que todos los campos necesarios estén rellenados
            if (dataIniciPicker.getValue() == null || 
                dataFiPicker.getValue() == null || 
                tipusReservaCombo.getValue() == null || 
                tipusIVACombo.getValue() == null || 
                tipusPensioCombo.getValue() == null) {
                return;
            }
            
            // Obtenemos los valores seleccionados
            String tipusReserva = tipusReservaCombo.getValue();
            String pensio = tipusPensioCombo.getValue();
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
            
            // Pillamos la habitacion y el tipo de pension del combobox
            Habitacio habitacion = numHab.getValue();
            String tipoPension = tipusPensioCombo.getValue();
            Double precioTotal = Double.valueOf(preuTotalField.getText());

            // Creamos la nueva reserva
            Reserva novaReserva = new Reserva(idPersona, dataReserva, dataInici, dataFi, tipusReserva, tipusIVA, tipoPension,habitacion, precioTotal);
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

    private void mostrarMissatge(Alert.AlertType tipus, String titol, String missatge) {
        Alert alert = new Alert(tipus);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }
}