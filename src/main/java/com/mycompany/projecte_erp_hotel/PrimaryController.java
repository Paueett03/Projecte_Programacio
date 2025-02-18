package com.mycompany.projecte_erp_hotel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class PrimaryController {

    @FXML
    private AnchorPane contentPane; // Panel es carregaràn les vistes

    private void carregarVista(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/projecte_erp_hotel/views/" + fxml));
            Parent view = loader.load();
            contentPane.getChildren().setAll(view); // Reemplaza el contingut central
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void carregarNouClient() {
        carregarVista("nou_client.fxml");
    }

    @FXML
    private void carregarNouEmpleat() {
        carregarVista("nou_empleat.fxml");
    }

    @FXML
    private void carregarNovaReserva() {
        carregarVista("nova_reserva.fxml");
    }

    @FXML
    private void carregarVeureReserves() {
        carregarVista("veure_reserves.fxml");
    }

    @FXML
    private void carregarGenerarFactures() {
        carregarVista("generar_factura.fxml");
    }

    @FXML
    private void carregarVeureFactures() {
        carregarVista("veure_factures.fxml");
    }

    @FXML
    private void carregarAsignarTasques() {
        carregarVista("asignar_tasques.fxml");
    }

    @FXML
    private void carregarVeureTasques() {
        carregarVista("veure_tasques.fxml");
    }

    @FXML
    private void carregarNouClientEmpleat() {
        carregarVista("Nou_client_i_empleat.fxml");
    }

    @FXML
    private void sortir() {
        System.exit(0); // Tanca l'aplicació
    }
}
