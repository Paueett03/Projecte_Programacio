module com.mycompany.projecte_erp_hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires  java.sql; 
    requires javafx.swing;
    requires java.desktop; 
    requires javafx.base;
    requires java.base;
    opens com.mycompany.projecte_erp_hotel to javafx.fxml;
    exports com.mycompany.projecte_erp_hotel;
}