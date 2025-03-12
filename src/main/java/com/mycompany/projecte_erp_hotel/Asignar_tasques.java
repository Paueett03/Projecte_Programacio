package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Connexio;
import com.mycompany.projecte_erp_hotel.model.Tasca;
import com.mycompany.projecte_erp_hotel.model.Empleat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Asignar_tasques {
    @FXML private ComboBox<String> empleats;
    @FXML private DatePicker dataExecucio;
    @FXML private TextArea descripcio;
    String estat = "Pendent";
    LocalDate dataCreacio = LocalDate.now();

    private ObservableList<String> empleatsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregarEmpleats();
    }

    private void carregarEmpleats() {
        String sql = "SELECT nom, cognom FROM Persona INNER JOIN Empleat ON Persona.id_persona = Empleat.id_empleat";
        try (Connection conn = new Connexio().connecta(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String nom = rs.getString("nom");
                String cognom = rs.getString("cognom");
                empleatsList.add(nom + " " + cognom);
            }
            
            empleats.setItems(empleatsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@FXML
private void guardarTasca() throws SQLException {
    if (empleats.getValue() == null || dataCreacio == null ||
        dataExecucio.getValue() == null || descripcio.getText().isEmpty()) {
        mostrarAlert("Error", "Tots els camps són obligatoris.", Alert.AlertType.ERROR);
        borrarCampos();
        return;
    }
    
    Tasca novaTasca = new Tasca(Date.valueOf(dataCreacio), Date.valueOf(dataExecucio.getValue()), descripcio.getText(), estat);
    novaTasca.insertarTasca(); // Inserim la tasca, però no obtenim l'ID directament aquí
    
    // Obtenir l'ID de l'empleat seleccionat
    String[] nomCognom = empleats.getValue().split(" ", 2);
    String nom = nomCognom[0];
    String cognom = nomCognom.length > 1 ? nomCognom[1] : "";
    
    String sqlEmpleat = "SELECT id_empleat FROM Persona INNER JOIN Empleat ON Persona.id_persona = Empleat.id_empleat WHERE nom = ? AND cognom = ?";
    try (Connection conn = new Connexio().connecta(); PreparedStatement stmt = conn.prepareStatement(sqlEmpleat)) {
        stmt.setString(1, nom);
        stmt.setString(2, cognom);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int idEmpleat = rs.getInt("id_empleat");

                // Assignar la tasca a l'empleat
                novaTasca.assignarTascaAEmpleat(idEmpleat);
                mostrarAlert("Èxit", "Tasca assignada correctament.", Alert.AlertType.INFORMATION);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        mostrarAlert("Error", "No s'ha pogut assignar la tasca a l'empleat.", Alert.AlertType.ERROR);
    }
}


    private void mostrarAlert(String titol, String missatge, Alert.AlertType tipus) {
        Alert alert = new Alert(tipus);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }

    @FXML
    public void borrarCampos() {
        dataExecucio.valueProperty().set(null);
        descripcio.clear();
        empleats.setValue(null);
    }
}