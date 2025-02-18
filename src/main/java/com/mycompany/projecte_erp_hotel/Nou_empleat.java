/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projecte_erp_hotel;

import com.mycompany.projecte_erp_hotel.model.Empleat;
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
public class Nou_empleat {

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
