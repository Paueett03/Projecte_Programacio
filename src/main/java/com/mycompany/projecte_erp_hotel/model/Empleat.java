package com.mycompany.projecte_erp_hotel.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Empleat extends Persona {
    private int idEmpleat;
    private String llocFeina;
    private LocalDate dataContratacio;
    private double salariBrut;
    private String estatLaboral;

    // Constructor
    public Empleat(String llocFeina, LocalDate dataContratacio, double salariBrut, 
                  String estatLaboral, String email, Date dataNaixement, String nom, 
                  String cognom, String adreça, String documentIdentitat, String telefon) {
        super(email, dataNaixement, nom, cognom, adreça, documentIdentitat, telefon);
        this.llocFeina = llocFeina;
        this.dataContratacio = dataContratacio;
        this.salariBrut = salariBrut;
        this.estatLaboral = estatLaboral;
    }

    public Empleat() {}

    // Getters y Setters
    public int getIdEmpleat() {
        return idEmpleat;
    }

    public void setIdEmpleat(int idEmpleat) {
        this.idEmpleat = idEmpleat;
    }

    public String getLlocFeina() {
        return llocFeina;
    }

    public void setLlocFeina(String llocFeina) {
        this.llocFeina = llocFeina;
    }

    public LocalDate getDataContratacio() {
        return dataContratacio;
    }

    public void setDataContratacio(LocalDate dataContratacio) {
        this.dataContratacio = dataContratacio;
    }

    public double getSalariBrut() {
        return salariBrut;
    }

    public void setSalariBrut(double salariBrut) {
        this.salariBrut = salariBrut;
    }

    public String getEstatLaboral() {
        return estatLaboral;
    }

    public void setEstatLaboral(String estatLaboral) {
        this.estatLaboral = estatLaboral;
    }

    // Método para verificar tanto por DNI como por email
    private int chckPersonaExistente(Connection conn, String documentIdentitat, String email) throws SQLException {
        String sql = "SELECT id_persona FROM Persona WHERE document_identitat = ? OR email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, documentIdentitat);
            pstmt.setString(2, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_persona");
            }
        }
        return -1;
    }

    // Méttodo para insertar los datos en la base de datos
    public boolean save(Empleat nouEmpleat) {
        String sqlPersona = "INSERT INTO Persona (email, data_naixement, nom, cognom, adreça, document_identitat, telefon) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlEmpleat = "INSERT INTO Empleat (id_persona, llocFeina, data_contractacio, salariBrut, estat_laboral) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = new Connexio().connecta()) {
            conn.setAutoCommit(false);
            
            try {
                // Primero verificamos si la persona existe (por DNI o email)
                int idPersona = chckPersonaExistente(conn, nouEmpleat.getDocumentIdentitat(), nouEmpleat.getEmail());
                
                if (idPersona != -1) {
                    // La persona existe, verificar si ya es empleado
                    String checkEmpleat = "SELECT id_empleat FROM Empleat WHERE id_persona = ?";
                    try (PreparedStatement pstmtCheck = conn.prepareStatement(checkEmpleat)) {
                        pstmtCheck.setInt(1, idPersona);
                        ResultSet rs = pstmtCheck.executeQuery();
                        if (rs.next()) {
                            // Ya existe como empleado
                            conn.rollback();
                            return false;
                        }
                        
                        // Existe como persona pero no como empleado, solo insertamos en Empleat
                        try (PreparedStatement pstmtEmpleat = conn.prepareStatement(sqlEmpleat)) {
                            pstmtEmpleat.setInt(1, idPersona);
                            pstmtEmpleat.setString(2, nouEmpleat.getLlocFeina());
                            pstmtEmpleat.setObject(3, nouEmpleat.getDataContratacio());
                            pstmtEmpleat.setDouble(4, nouEmpleat.getSalariBrut());
                            pstmtEmpleat.setString(5, nouEmpleat.getEstatLaboral());
                            pstmtEmpleat.executeUpdate();
                        }
                    }
                } else {
                    // La persona no existe, insertamos en ambas tablas
                    try (PreparedStatement pstmtPersona = conn.prepareStatement(sqlPersona, PreparedStatement.RETURN_GENERATED_KEYS)) {
                        pstmtPersona.setString(1, nouEmpleat.getEmail());
                        pstmtPersona.setObject(2, nouEmpleat.getDataNaixement());
                        pstmtPersona.setString(3, nouEmpleat.getNom());
                        pstmtPersona.setString(4, nouEmpleat.getCognom());
                        pstmtPersona.setString(5, nouEmpleat.getAdreça());
                        pstmtPersona.setString(6, nouEmpleat.getDocumentIdentitat());
                        pstmtPersona.setString(7, nouEmpleat.getTelefon());
                        pstmtPersona.executeUpdate();

                        try (ResultSet generatedKeys = pstmtPersona.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                idPersona = generatedKeys.getInt(1);
                                
                                // Insertar nuevo empleado
                                try (PreparedStatement pstmtEmpleat = conn.prepareStatement(sqlEmpleat)) {
                                    pstmtEmpleat.setInt(1, idPersona);
                                    pstmtEmpleat.setString(2, nouEmpleat.getLlocFeina());
                                    pstmtEmpleat.setObject(3, nouEmpleat.getDataContratacio());
                                    pstmtEmpleat.setDouble(4, nouEmpleat.getSalariBrut());
                                    pstmtEmpleat.setString(5, nouEmpleat.getEstatLaboral());
                                    pstmtEmpleat.executeUpdate();
                                }
                            }
                        }
                    }
                }
                
                conn.commit();
                return true;
                
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}