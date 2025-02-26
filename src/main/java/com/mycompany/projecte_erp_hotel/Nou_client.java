package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Client;
import java.sql.Date;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Controlador FXML para la creación de nuevos clientes.
 */
public class Nou_client {

    @FXML
    private TextField nom;

    @FXML
    private TextField cognom;

    @FXML
    private TextField adreca;

    @FXML
    private TextField documentIdentitat;

    @FXML
    private DatePicker dataNaixement;

    @FXML
    private TextField telefon;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<String> tipus;

    @FXML
    private TextField targeta;

    @FXML
    private DatePicker dataRegistre;

    @FXML
    public void initialize() {
        // Crear una lista observable de tipos
        ObservableList<String> options = FXCollections.observableArrayList(
                "Estudiant", "Treballador", "Aturat"
        );

        // Asignar la lista al ComboBox
        tipus.setItems(options);
    }

 @FXML
    public void guardarComoCliente() {
        try {
            // Validaciones básicas
            if (nom.getText().isEmpty() || cognom.getText().isEmpty() || 
                email.getText().isEmpty() || documentIdentitat.getText().isEmpty()) {
                mostrarAlertError("Els camps Nom, Cognom, Email i Document d'Identitat són obligatoris.");
                return;
            }

            // Verificar si las fechas son nulas antes de convertirlas
            LocalDate dataNaixementValue = this.dataNaixement.getValue();
            Date sqlDateNaixement = (dataNaixementValue != null) ? Date.valueOf(dataNaixementValue) : null;

            LocalDate dataRegistreValue = this.dataRegistre.getValue();
            if (dataRegistreValue == null) {
                mostrarAlertError("La data de registre és obligatòria.");
                return;
            }

            // Crear un objeto Cliente con los valores obtenidos
            Client nuevoCliente = new Client(
                dataRegistreValue,
                tipus.getValue(),
                targeta.getText(),
                email.getText(),
                sqlDateNaixement,
                nom.getText(),
                cognom.getText(),
                adreca.getText(),
                documentIdentitat.getText(),
                telefon.getText()
            );

            // Guardar el cliente en la base de datos
            boolean clienteGuardado = nuevoCliente.save(nuevoCliente);

            if (clienteGuardado) {
                mostrarAlertInfo("Client desat correctament.");
                borrarCampos();
            } else {
                mostrarAlertError("No s'ha pogut desar el client. Potser ja existeix.");
            }

        } catch (Exception e) {
            mostrarAlertError("Error inesperat: " + e.getMessage());
        }
    }

    @FXML
    public void borrarCampos() {
        nom.clear();
        cognom.clear();
        adreca.clear();
        documentIdentitat.clear();
        targeta.clear();
        telefon.clear();
        email.clear();
        dataRegistre.getEditor().clear();
        dataNaixement.getEditor().clear();
        tipus.valueProperty().set(null);
    }

    @FXML
    private void mostrarAlertError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void mostrarAlertInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
