/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Client;
import com.mycompany.projecte_erp_hotel.model.Persona;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author alumne
 */
public class Nou_client {

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

}
