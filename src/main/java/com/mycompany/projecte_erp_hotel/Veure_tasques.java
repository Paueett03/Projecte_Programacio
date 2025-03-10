package com.mycompany.projecte_erp_hotel;
import com.mycompany.projecte_erp_hotel.model.Connexio;
import com.mycompany.projecte_erp_hotel.model.Tasca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Veure_tasques {

    @FXML
    private TextField txtBuscarEmpleat;
    @FXML
    private ListView<String> listViewTasques;
    @FXML
    private Button btnActualitzar;

    public void initialize() {
        // Configurar el botón para actualizar las tareas
        btnActualitzar.setOnAction(event -> actualitzarTasques());
    }

    private void actualitzarTasques() {
        String nomEmpleat = txtBuscarEmpleat.getText().trim();
        
        if (nomEmpleat.isEmpty()) {
            mostrarAlerta("Error", "El nom de l'empleat no pot estar buit.");
            return;
        }
        
        try {
            int idEmpleat = obtenirIdEmpleatPerNom(nomEmpleat);

            if (idEmpleat == -1) {
                mostrarAlerta("Error", "No s'ha trobat cap empleat amb aquest nom.");
                return;
            }

            // Crear una nueva tasca (aquí puedes personalizar los datos)
//            Tasca novaTasca = new Tasca();
//            novaTasca.setdataCreacio(new java.sql.Date(System.currentTimeMillis())); // Fecha actual
//            novaTasca.setdataExecucio(null); // La puedes dejar vacía o pedirla al usuario
//            novaTasca.setdescripcio("Nova tasca assignada");
//            novaTasca.setestat("Pendent");

            // Insertar y asignar la tasca
//            novaTasca.assignarTascaAEmpleat(idEmpleat);

            // Actualizar la lista de tareas
//            carregarTasquesEmpleat(idEmpleat);

            mostrarAlerta("Èxit", "Tasca creada i assignada correctament!");

        } catch (SQLException e) {
            mostrarAlerta("Error", "No s'ha pogut actualitzar les tasques: " + e.getMessage());
        }
    }

    private int obtenirIdEmpleatPerNom(String nomEmpleat) throws SQLException {
        Connection conn = new Connexio().connecta();
        String sql = "SELECT id_empleat FROM Empleat WHERE nom = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomEmpleat);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id_empleat");
            }
        }
        return -1; // Si no encuentra al empleado
    }

//    private void carregarTasquesEmpleat(int idEmpleat) throws SQLException {
//        Connection conn = new Connexio().connecta();
//        String sql = """
//            SELECT t.descripcio, t.estat
//            FROM Tasca t
//            INNER JOIN Assig_Empleat_Tasca aet ON t.id_tasca = aet.id_tasca
//            WHERE aet.id_empleat = ?
//        """;
//        
//        listViewTasques.getItems().clear();
//        
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, idEmpleat);
//            ResultSet rs = stmt.executeQuery();
//            
//            while (rs.next()) {
//                String descripcio = rs.getString("descripcio");
//                String estat = rs.getString("estat");
//                listViewTasques.getItems().add(descripcio + " - " + estat);
//            }
//        }
//    }

    private void mostrarAlerta(String titol, String missatge) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titol);
        alert.setHeaderText(null);
        alert.setContentText(missatge);
        alert.showAndWait();
    }
}
