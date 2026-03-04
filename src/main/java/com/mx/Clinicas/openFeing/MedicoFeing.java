package com.mx.Clinicas.openFeing;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mx.Clinicas.dto.MedicoModel;


@FeignClient(name = "Medicos",url = "http://localhost:9002",path = "/medicos")
public interface MedicoFeing {
    @GetMapping("buscarPorClinica/{numero}")
    public List<MedicoModel>buscarMedicos(@PathVariable("numero")long numero);
}
