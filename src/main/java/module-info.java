module com.mycompany.projecte_erp_hotel {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.projecte_erp_hotel to javafx.fxml;
    exports com.mycompany.projecte_erp_hotel;
}
