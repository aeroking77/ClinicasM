package com.mx.Clinicas.service;

import java.util.HashMap;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.mx.Clinicas.dao.ClinicaDao;
import com.mx.Clinicas.dto.MedicoModel;
import com.mx.Clinicas.dto.PacienteModel;
import com.mx.Clinicas.dto.Respuesta;
import com.mx.Clinicas.entidad.Clinica;
import com.mx.Clinicas.openFeing.MedicoFeing;
import com.mx.Clinicas.openFeing.PacienteFeing;

@Service
public class ClinicaServicio {
    private ClinicaDao clinicaDao;
    private MedicoFeing medicoFeing;
    private PacienteFeing pacienteFeing;
    
    public ClinicaServicio(ClinicaDao clinicaDao,PacienteFeing pacienteFeing,MedicoFeing medicoFeing) {
        this.clinicaDao = clinicaDao;
        this.pacienteFeing = pacienteFeing;
        this.medicoFeing = medicoFeing;
    }
    
    public ResponseEntity<List<Clinica>> listar(){
        if(clinicaDao.findAll().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clinicaDao.findAll());
    }
    
    public Respuesta guardar(Clinica clinica) {
        Respuesta rs = new Respuesta();
        if(clinicaDao.existsById(clinica.getNumero())) {
            rs.setMensaje("La clinica no se agrego por que sumero ya existe");
            rs.setSuccess(false);
            rs.setObj(clinica.getNumero());
            return rs;
        }
        for(Clinica c: clinicaDao.findAll()) {
            if(clinica.getNombre().equalsIgnoreCase(c.getNombre())) {
                rs.setMensaje("La clinica no se agrego por que su nombre ya existe");
                rs.setSuccess(false);
                rs.setObj(c);
                return rs;
            } 
        }
        clinicaDao.save(clinica);
        rs.setMensaje("La clinica ha sido agregada");
        rs.setSuccess(true);
        rs.setObj(clinica);
        return rs;
    }
    
    public Respuesta editar(Clinica clinica) {
        Respuesta rs = new Respuesta();
        if(clinicaDao.existsById(clinica.getNumero())) {
            for(Clinica c: clinicaDao.findAll()) {
                if(clinica.getNumero() != c.getNumero() && clinica.getNombre().equalsIgnoreCase(c.getNombre())) {
                    rs.setMensaje("La clinica no ha sido editada por que el nombre ya existe");
                    rs.setSuccess(false);
                    rs.setObj(c);
                    return rs;
                }
            }
            clinicaDao.save(clinica);
            rs.setMensaje("La clinica ha sido editada");
            rs.setSuccess(true);
            rs.setObj(clinica);
            return rs;
        }
        rs.setMensaje("La clinica que tratas de editar no exiate");
        rs.setSuccess(false);
        rs.setObj(clinica.getNombre());
        return rs;
    }
    
    public Respuesta eliminar(long numero) {
        Respuesta rs = new Respuesta();
        Clinica clinica = clinicaDao.findById(numero).orElse(null);
        if(clinica == null) {
            rs.setMensaje("La clinica que tratas de eliminar no existe");
            rs.setObj(numero);
            rs.setSuccess(false);
            return rs;
        }
        List<PacienteModel>pacientes = pacienteFeing.buscarPacientes(numero);
        if(pacientes != null) {
            rs.setMensaje("La clinica no se puede eliminar por que tiene pacientes");
            rs.setSuccess(false);
            rs.setObj(pacientes);
            return rs;
        }
        List<MedicoModel>medicos = medicoFeing.buscarMedicos(numero);
        if(medicos != null) {
            rs.setMensaje("La clinica no se puede eliminar por que tiene medicos");
            rs.setSuccess(false);
            rs.setObj(medicos);
            return rs;
        }
        rs.setObj(clinica);
        clinicaDao.delete(clinica);
        rs.setMensaje("La clinica ha sido eliminada");
        rs.setSuccess(true);
        return rs;
    }
    
    public ResponseEntity<Clinica>buscar(long numero){
        Clinica clinica = clinicaDao.findById(numero).orElse(null);
        if(clinica == null) {
        	return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clinica);
    }
    
    public ResponseEntity<List<PacienteModel>>buscarPacientes(long numero){
        List<PacienteModel>pacientes = pacienteFeing.buscarPacientes(numero);
        if(pacientes == null) {
        	return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pacientes);
    }
    
    public ResponseEntity<List<MedicoModel>>buscarMedicos(long numero){
        List<MedicoModel>medicos = medicoFeing.buscarMedicos(numero);
        if(medicos == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(medicos);
    }
    
    public ResponseEntity<HashMap<String,Object>> buscarTodo(long numero){
    	HashMap<String, Object>hash = new HashMap<>();
        List<PacienteModel> pacientes = pacienteFeing.buscarPacientes(numero);
        hash.put("Pacientes", pacientes);
        List<MedicoModel> medicos = medicoFeing.buscarMedicos(numero);
        hash.put("Medicos", medicos);
        return ResponseEntity.ok(hash);
    }
}
