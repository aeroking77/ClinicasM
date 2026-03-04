package com.mx.Clinicas.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mx.Clinicas.entidad.Clinica;

public interface ClinicaDao extends JpaRepository<Clinica, Long> {

}
