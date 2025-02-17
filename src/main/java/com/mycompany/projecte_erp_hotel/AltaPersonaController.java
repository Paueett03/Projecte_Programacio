package com.mycompany.projecte_erp_hotel;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.mycompany.projecte_erp_hotel.model.Persona;
import com.mycompany.projecte_erp_hotel.model.Client;
import com.mycompany.projecte_erp_hotel.model.Empleat;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;

public class AltaPersonaController {

    @FXML
    private TextField id;
    @FXML
    private TextField nom;

    @FXML
    private TextField cognom;

    @FXML
    private TextField adreca;

    @FXML
    private TextField documentIdentitat;

    @FXML
    private DatePicker dataNaixement;

    @FXML
    private TextField telefon;

    @FXML
    private TextField email;

    @FXML
    public void guardarComoCliente() {
        // Crear una nueva Persona
        Persona persona = new Persona();
        persona.setEmail(email.getText());
        persona.setData_naixement(dataNaixement.getValue());
        persona.setNom(nom.getText());
        persona.setCognom(cognom.getText());
        persona.setAdreça(adreca.getText());
        persona.setDocument_identitat(documentIdentitat.getText());
        persona.setTelefon(telefon.getText());

        // Crear un nuevo Cliente a partir de la Persona
        Client client = new Client();
        client.setData_registre(client.getData_registre());
        client.getTipus_client();
        client.getTargeta_credit();
        client.setNom(persona.getNom());
        client.setCognom(persona.getCognom());
        client.setAdreça(persona.getAdreça());
        client.setDocument_identitat(persona.getDocument_identitat());
        client.setData_naixement(persona.getData_naixement());
        client.setTelefon(persona.getTelefon());
        client.setEmail(persona.getEmail());

        // Guardar el cliente en la base de datos
        client.save();

        // Cerrar la ventana de alta de persona
        nom.getScene().getWindow().hide();
    }

    @FXML
    private void guardarComoEmpleado() {
        // Crear una nueva Persona
        Persona persona = new Persona();
        persona.setNom(nom.getText());
        persona.setCognom(cognom.getText());
        persona.setAdreça(adreca.getText());
        persona.setDocument_identitat(documentIdentitat.getText());
        persona.setTelefon(telefon.getText());
        persona.setEmail(email.getText());

        // Crear un nuevo Empleado a partir de la Persona
        Empleat empleat = new Empleat();
        empleat.setNom(persona.getNom());
        empleat.setCognom(persona.getCognom());
        empleat.setAdreça(persona.getAdreça());
        empleat.setDocument_identitat(persona.getDocument_identitat());
        empleat.setData_naixement(persona.getData_naixement());
        empleat.setTelefon(persona.getTelefon());
        empleat.setEmail(persona.getEmail());

//        // Guardar el empleado en la base de datos
//        empleat.save();
        // Cerrar la ventana de alta de persona
        nom.getScene().getWindow().hide();
    }

    @FXML
    public void initialize() {
        
    }

}
