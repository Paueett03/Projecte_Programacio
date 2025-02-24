package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Empleat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Date;
import java.time.LocalDate;

public class Nou_empleat {
    @FXML private TextField nom;
    @FXML private TextField cognom;
    @FXML private TextField adreça;
    @FXML private TextField documentIdentitat;
    @FXML private TextField telefon;
    @FXML private TextField email;
    @FXML private DatePicker dataNaixement;
    @FXML private DatePicker dataContratacio;
    @FXML private TextField salariBrut;
    @FXML private ComboBox<String> llocFeina;
    @FXML private ComboBox<String> estatLaboral;

    @FXML
    public void initialize() {
        // Inicializar ComboBox de lloc de feina
        ObservableList<String> llocFeines = FXCollections.observableArrayList(
            "Recepcionista", "Cambrer/a", "Cuiner/a", "Personal de neteja", 
            "Manteniment", "Director/a", "Administratiu/va"
        );
        llocFeina.setItems(llocFeines);

        // Inicializar ComboBox de estat laboral
        ObservableList<String> estats = FXCollections.observableArrayList(
            "Actiu", "Baixa", "Permís"
        );
        estatLaboral.setItems(estats);
    }

    @FXML
    public void guardarEmpleat() {
        try {
            // Validaciones
            if (nom.getText().isEmpty() || cognom.getText().isEmpty() || 
                documentIdentitat.getText().isEmpty() || email.getText().isEmpty() ||
                salariBrut.getText().isEmpty() || llocFeina.getValue() == null || 
                estatLaboral.getValue() == null) {
                mostrarAlertError("Tots els camps són obligatoris.");
                return;
            }

            // Convertir fechas
            LocalDate dataNaixementValue = dataNaixement.getValue();
            Date sqlDateNaixement = (dataNaixementValue != null) ? Date.valueOf(dataNaixementValue) : null;

            LocalDate dataContratacioValue = dataContratacio.getValue();
            if (dataContratacioValue == null) {
                mostrarAlertError("La data de contractació és obligatòria.");
                return;
            }

            // Validar salario
            double salariBrutValue;
            try {
                salariBrutValue = Double.parseDouble(salariBrut.getText());
                if (salariBrutValue <= 0) {
                    mostrarAlertError("El salari ha de ser un valor positiu.");
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarAlertError("El salari ha de ser un número vàlid.");
                return;
            }

            // Crear nuevo empleado
            Empleat nouEmpleat = new Empleat(
                llocFeina.getValue(),
                dataContratacioValue,
                salariBrutValue,
                estatLaboral.getValue(),
                email.getText(),
                sqlDateNaixement,
                nom.getText(),
                cognom.getText(),
                adreça.getText(),
                documentIdentitat.getText(),
                telefon.getText()
            );

            // Guardar empleado
            if (nouEmpleat.save(nouEmpleat)) {
                mostrarAlertInfo("Empleat desat correctament.");
                borrarCamps();
            } else {
                mostrarAlertError("No s'ha pogut desar l'empleat. Ja existeix.");
            }

        } catch (Exception e) {
            mostrarAlertError("Error inesperat: " + e.getMessage());
        }
    }

    @FXML
    private void borrarCamps() {
        nom.clear();
        cognom.clear();
        adreça.clear();
        documentIdentitat.clear();
        telefon.clear();
        email.clear();
        dataNaixement.setValue(null);
        dataContratacio.setValue(null);
        salariBrut.clear();
        llocFeina.setValue(null);
        estatLaboral.setValue(null);
    }

    private void mostrarAlertError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}