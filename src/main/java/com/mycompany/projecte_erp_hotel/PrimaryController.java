package com.mycompany.projecte_erp_hotel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class PrimaryController {

    @FXML
    private MenuItem nuevoClienteMenuItem;

    @FXML
    private void initialize() {
        nuevoClienteMenuItem.setOnAction(event -> {
            try {
                // Cargar el archivo FXML de la nueva escena
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/projecte_erp_hotel/altaPersona.fxml"));
                Parent root = loader.load();

                // Crear una nueva ventana
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Alta de Persona");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });




    }
}