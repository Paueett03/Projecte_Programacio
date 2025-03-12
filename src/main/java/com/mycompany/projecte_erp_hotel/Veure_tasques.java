package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Connexio;
import com.mycompany.projecte_erp_hotel.model.Tasca;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Veure_tasques {

    @FXML private TextField buscarEmpleat;
    @FXML private ListView<String> llistaTasques;

    private ObservableList<String> tasquesList = FXCollections.observableArrayList(); // Llista observable per a les tasques

    @FXML
    public void initialize() {
        llistaTasques.setItems(tasquesList); // Enllaçar la llista observable amb la vista
    }

    @FXML
    private void buscarTasques() {
        tasquesList.clear();
        String nomComplet = buscarEmpleat.getText().trim(); // Obtenir el text introduït i eliminar espais sobrants
        if (nomComplet.isEmpty()) { // Verificar si el camp està buit
            mostrarAlert("Error", "Has d'introduir el nom de l'empleat.", Alert.AlertType.ERROR);
            return; // Sortir del mètode si no hi ha nom
        }
        
        String[] nomParts = nomComplet.split(" ", 2); // Dividir el text en nom i cognom
        if (nomParts.length < 2) { // Verificar si s'ha introduït nom i cognom
            mostrarAlert("Error", "Introdueix nom i cognom.", Alert.AlertType.ERROR);
            return; // Sortir del mètode si falta alguna part
        }
        
        String nom = nomParts[0]; // Assignar la primera part com a nom
        String cognom = nomParts[1]; // Assignar la segona part com a cognom

        // Consulta SQL per trobar les tasques de l'empleat
        String sql = "SELECT t.descripcio, t.data_creacio, t.data_execucio, t.estat " +
                     "FROM Tasca t " +
                     "INNER JOIN Assig_Empleat_Tasca a ON t.id_tasca = a.id_tasca " +
                     "INNER JOIN Empleat e ON a.id_empleat = e.id_empleat " +
                     "INNER JOIN Persona p ON e.id_empleat = p.id_persona " +
                     "WHERE p.nom = ? AND p.cognom = ?";

        try (Connection conn = new Connexio().connecta(); // Obrir la connexió a la base de dades
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Preparar la consulta SQL

            stmt.setString(1, nom); // Assignar el nom al primer paràmetre de la consulta
            stmt.setString(2, cognom); // Assignar el cognom al segon paràmetre de la consulta

            try (ResultSet rs = stmt.executeQuery()) { // Executar la consulta i obtenir els resultats
                while (rs.next()) { // Iterar pels resultats trobats
                    String descripcio = rs.getString("descripcio"); // Obtenir la descripció de la tasca
                    Date dataCreacio = rs.getDate("data_creacio"); // Obtenir la data de creació
                    Date dataExecucio = rs.getDate("data_execucio"); // Obtenir la data d'execució
                    String estat = rs.getString("estat"); // Obtenir l'estat de la tasca
                    
                    // Format de la informació de la tasca
                    String tascaInfo = String.format("%s - Creació: %s, Execució: %s, Estat: %s",
                            descripcio, dataCreacio, dataExecucio, estat);
                    tasquesList.add(tascaInfo); // Afegir la informació a la llista
                }
                
                if (tasquesList.isEmpty()) { // Si no s'han trobat tasques
                    mostrarAlert("Informació", "No s'han trobat tasques per a aquest empleat.", Alert.AlertType.INFORMATION);
                }
            }
        } catch (SQLException e) { // Capturar errors de SQL
            mostrarAlert("Error", "Error en carregar les tasques: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace(); // Imprimir l'error a la consola per depuració
        }
    }

    private void mostrarAlert(String titol, String missatge, Alert.AlertType tipus) {
        Alert alert = new Alert(tipus); // Crear una alerta
        alert.setTitle(titol); // Establir el títol de l'alerta
        alert.setHeaderText(null); // No mostrar capçalera
        alert.setContentText(missatge); // Establir el missatge de l'alerta
        alert.showAndWait(); // Mostrar l'alerta i esperar que l'usuari la tanqui
    }
}
