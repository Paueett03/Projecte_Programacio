package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Factura;
import com.mycompany.projecte_erp_hotel.model.Connexio;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Veure_factures {

    @FXML
    private TableView<Factura> facturaTable; // Taula per mostrar les factures

    @FXML
    private TableColumn<Factura, Integer> idFacturaCol; // Columna per l'ID de la factura

    @FXML
    private TableColumn<Factura, String> dataEmisioCol; // Columna per la data d'emissió

    @FXML
    private TableColumn<Factura, String> metodePagamentCol; // Columna pel mètode de pagament

    @FXML
    private TableColumn<Factura, Double> baseImposableCol; // Columna per la base imposable

    @FXML
    private TableColumn<Factura, Double> ivaCol; // Columna per l'IVA

    @FXML
    private TableColumn<Factura, Double> totalCol; // Columna pel total de la factura

    @FXML
    public void initialize() {
        // Configurar les columnes de la taula amb els atributs de la classe Factura
        idFacturaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId_factura()));
        dataEmisioCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getData_emisio().toString()));
        metodePagamentCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getMetode_pagament()));
        baseImposableCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getBase_imposable()));
        ivaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIva()));
        totalCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTotal()));

        // Carregar les factures a la taula
        carregarFactures();
    }

    private void carregarFactures() {
        ObservableList<Factura> facturaList = FXCollections.observableArrayList();

        // Consulta SQL per obtenir totes les factures
        String sql = "SELECT * FROM Factura";
        try (Connection conn = new Connexio().connecta();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Recórrer els resultats i crear objectes Factura
            while (rs.next()) {
                Factura factura = new Factura(
                        rs.getDouble("base_imposable"),
                        rs.getDate("data_emisio").toLocalDate(),
                        rs.getString("metode_pagament"),
                        rs.getDouble("iva"),
                        rs.getDouble("total")
                );
                factura.setId_factura(rs.getInt("id_factura"));
                facturaList.add(factura);
            }

            // Afegir les factures a la taula
            facturaTable.setItems(facturaList);
        } catch (SQLException e) {
            mostrarMissatge(Alert.AlertType.ERROR, "Error de base de dades", "No s'han pogut carregar les factures: " + e.getMessage());
        }
    }

    private void mostrarMissatge(Alert.AlertType tipus, String titol, String missatge) {
        // Mostrar una alerta amb el tipus, títol i missatge especificats
        Alert alert = new Alert(tipus);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }
}
