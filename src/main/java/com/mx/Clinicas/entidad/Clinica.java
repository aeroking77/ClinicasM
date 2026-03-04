package com.mx.Clinicas.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Clinicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clinica {
    private long numero;
    private String nombre;
    private String ciudad;
}
