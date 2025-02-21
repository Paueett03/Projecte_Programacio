package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Empleat;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class Nou_empleat {

    @FXML
    private TextField nom;
    @FXML
    private TextField cognom;
    @FXML
    private TextField adreça;
    @FXML
    private TextField documentIdentitat;
    @FXML
    private TextField telefon;
    @FXML
    private TextField email;
    @FXML
    private TextField dataNaixement;

    @FXML
    private void guardarEmpleat() {
        String nomText = nom.getText();
        String cognomText = cognom.getText();
        String adreçaText = adreça.getText();
        String documentIdentitatText = documentIdentitat.getText();
        String telefonText = telefon.getText();
        String emailText = email.getText();
        String dataNaixementText = dataNaixement.getText();

        if (nomText.isEmpty() || cognomText.isEmpty() || adreçaText.isEmpty() || documentIdentitatText.isEmpty() ||
            telefonText.isEmpty() || emailText.isEmpty() || dataNaixementText.isEmpty()) {
            mostrarAlerta("Tots els camps són obligatoris!");
        } else {
            try {
                Empleat nouEmpleat = new Empleat(nomText, cognomText, adreçaText, documentIdentitatText, telefonText, emailText, dataNaixementText);
                nouEmpleat.inserirEmpleat();  // Inserción delegada en el modelo
                mostrarAlerta("Nou empleat afegit correctament!");
            } catch (SQLException e) {
                mostrarAlerta("Error en desar l'empleat: " + e.getMessage());
            }
        }
    }

    private void mostrarAlerta(String missatge) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Informació");
        alerta.setHeaderText(null);
        alerta.setContentText(missatge);
        alerta.showAndWait();
    }
}
