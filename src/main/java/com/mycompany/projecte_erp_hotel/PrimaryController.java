package com.mycompany.projecte_erp_hotel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class PrimaryController {

    @FXML
    private BorderPane mainPane; // Se enlaza con el fx:id en el FXML

    // Método genérico para cargar una nueva vista
    private void cambiarVista(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent nuevaVista = loader.load();
            mainPane.setCenter(nuevaVista); // Cambia el centro del BorderPane
        } catch (IOException e) {
        }
    }

    // Métodos para cada opción del menú
    @FXML
    private void cargarNuevoEmpleado() {
        cambiarVista("/com/mycompany/projecte_erp_hotel/nou_empleat.fxml");
    }

    @FXML
    private void cargarNuevoCliente() {
        if (getClass().getResource("/com/mycompany/projecte_erp_hotel/nou_client.fxml") == null) {
            System.out.println("Error: No se encontró el archivo FXML.");
            return;
        }
        cambiarVista("/com/mycompany/projecte_erp_hotel/nou_client.fxml");
    }

    @FXML
    private void cargarNuevaReserva() {
        if (getClass().getResource("/com/mycompany/projecte_erp_hotel/nova_reserva.fxml") == null) {
            System.out.println("Error: No se encontró el archivo FXML.");
            return;
        }
        cambiarVista("/com/mycompany/projecte_erp_hotel/nova_reserva.fxml");
    }

    @FXML
    private void cargarVerReservas() {
        cambiarVista("/com/mycompany/projecte_erp_hotel/veure_reserves.fxml");
    }

    @FXML
    private void cargarNuevaTarea() {
        if (getClass().getResource("/com/mycompany/projecte_erp_hotel/asignar_tasques.fxml") == null) {
            System.out.println("Error: No se encontró el archivo FXML.");
            return;
        }
        cambiarVista("/com/mycompany/projecte_erp_hotel/asignar_tasques.fxml");
    }

    @FXML
    private void cargarVerTareas() {
        if (getClass().getResource("/com/mycompany/projecte_erp_hotel/veure_tasques.fxml") == null) {
            System.out.println("Error: No se encontró el archivo FXML.");
            return;
        }
        cambiarVista("/com/mycompany/projecte_erp_hotel/veure_tasques.fxml");
    }

    @FXML
    private void cargarVerFacturas() {
        cambiarVista("/com/mycompany/projecte_erp_hotel/veure_factures.fxml");
    }

    @FXML
    private void cargarGenerarFacturas() {
        cambiarVista("/com/mycompany/projecte_erp_hotel/generar_factura.fxml");
    }

    @FXML
    private void cerrarPrograma() {
        System.exit(0); // Cierra la aplicación
    }
    
    
    @FXML
    private void initialize(){
        cambiarVista("/com/mycompany/projecte_erp_hotel/Benvinguda.fxml");
    }
    

}
