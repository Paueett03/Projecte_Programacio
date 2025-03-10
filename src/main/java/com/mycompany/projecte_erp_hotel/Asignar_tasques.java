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
    // Definición de los elementos de la interfaz de usuario
    @FXML private ComboBox<String> empleats;
    @FXML private DatePicker dataExecucio;
    @FXML private TextArea descripcio;
    String estat = "Pendent";
    LocalDate dataCreacio = LocalDate.now();

    private ObservableList<String> empleatsList = FXCollections.observableArrayList();
    @FXML
    public void initialize() {
        carregarEmpleats(); // Carga la lista de empleados en el ComboBox
    }

private void carregarEmpleats() {
    // Consulta SQL para obtener solo los nombres de los empleados
    String sql = "SELECT nom, cognom FROM Persona INNER JOIN Empleat ON Persona.id_persona = Empleat.id_empleat";

    try (Connection conn = new Connexio().connecta(); 
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        while (rs.next()) {
            String nom = rs.getString("nom");
            String cognom = rs.getString("cognom");
            empleatsList.add(nom+" "+cognom); // Añadir el nombre i el apellido a la lista
        }

        empleats.setItems(empleatsList); // Asignar la lista al ComboBox
    } catch (SQLException e) {
        e.printStackTrace(); // Mostrar errores en consola
    }
}

    @FXML
    private void guardarTasca() throws SQLException {
        // Verifica que todos los campos estén completos
        if (empleats.getValue() == null || dataCreacio == null ||
            dataExecucio.getValue() == null || descripcio.getText().isEmpty()) {
            mostrarAlert("Error", "Tots els camps són obligatoris.", Alert.AlertType.ERROR);
            borrarCampos();
        }
        
        // Crea una nueva tarea con los datos
        Tasca novaTasca = new Tasca(Date.valueOf(dataCreacio), Date.valueOf(dataExecucio.getValue()), descripcio.getText(), estat);
        novaTasca.insertarTasca(); // Inserta la tarea en la base de datos
        mostrarAlert("Èxit", "Tasca assignada correctament.", Alert.AlertType.INFORMATION);
    }

    private void mostrarAlert(String titol, String missatge, Alert.AlertType tipus) {
        // Muestra una alerta con un mensaje específico
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
