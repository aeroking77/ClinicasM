package com.mx.Clinicas.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteModel {
    private String curp;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String genero;
    private double altura;
    private double peso;
    private int idMedico;
    private long numeroClinica;
}
