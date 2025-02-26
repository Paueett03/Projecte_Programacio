package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Connexio;
import com.mycompany.projecte_erp_hotel.model.Tasca;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Veure_tasques {
    // Elementos de la interfaz de usuario
    @FXML private TableView<Tasca> tasquesTable;
    @FXML private TableColumn<Tasca, String> descripcioColumn;
    @FXML private TableColumn<Tasca, String> estatColumn;
    @FXML private TableColumn<Tasca, String> dataExecucioColumn;
    @FXML private ComboBox<String> filtreEstat;
    
    private ObservableList<Tasca> tasquesList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas de la tabla
        descripcioColumn.setCellValueFactory(new PropertyValueFactory<>("descripcio"));
        estatColumn.setCellValueFactory(new PropertyValueFactory<>("estat"));
        dataExecucioColumn.setCellValueFactory(new PropertyValueFactory<>("data_execucio"));

        // Configurar opciones del filtro de estado
        filtreEstat.setItems(FXCollections.observableArrayList("Totes", "Pendent", "Completada"));
        filtreEstat.setValue("Totes");

        carregarTasques(); // Cargar las tareas desde la base de datos
    }

    private void carregarTasques() {
        // Consulta SQL para obtener todas las tareas
        String sql = "SELECT id_tasca, data_creacio, data_execucio, descripcio, estat FROM Tasca";
        try (Connection conn = new Connexio().connecta();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                tasquesList.add(new Tasca(
                        rs.getDate("data_creacio"),
                        rs.getDate("data_execucio"),
                        rs.getString("descripcio"),
                        rs.getString("estat")
                ));
            }
            tasquesTable.setItems(tasquesList);
        } catch (SQLException e) {
            e.printStackTrace(); // Mostrar errores en la consola
        }
    }
}
