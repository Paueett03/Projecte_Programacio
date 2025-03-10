package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Connexio;
import com.mycompany.projecte_erp_hotel.model.Reserva;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Veure_reserves {

    @FXML
    private ListView<String> llistaReserves; // Llista per mostrar les reserves

    @FXML
    private Label missatgeError; // Etiqueta per mostrar missatges d'error

    public void carregarReserves() {
        ObservableList<String> reserves = FXCollections.observableArrayList();
        try (Connection conn = new Connexio().connecta();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Reserva");
             ResultSet rs = stmt.executeQuery()) {

            // Recorrem els resultats de la consulta i afegim cada reserva a la llista
            while (rs.next()) {
                String reserva = "ID: " + rs.getInt("id_reserva") + ", Inici: " + rs.getDate("data_inici") + 
                                 ", Fi: " + rs.getDate("data_fi") + ", Total: " + rs.getDouble("preu_total_reserva") + "€";
                reserves.add(reserva);
            }
            // Assignem les reserves a la llista
            llistaReserves.setItems(reserves);
        } catch (SQLException e) {
            // Capturem qualsevol error en carregar les reserves
            missatgeError.setText("Error carregant reserves: " + e.getMessage());
        }
    }
    public void initialize(){
        carregarReserves();
    }
}