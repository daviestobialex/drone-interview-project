/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mulsalasoft.interview.drones;

import com.mulsalasoft.interview.drones.entities.Medication;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daviestobialex
 */
@RestController("mediactions")
public class MedicationResourceController implements ControllerStrategy<Medication> {

    private final DataService dataService;

    @Autowired
    public MedicationResourceController(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void create(Medication request) {
        dataService.getMedicationRepo().save(request);
    }

    @Override
    public Medication read(Long id) {
        EntityNotFoundException enfe = new EntityNotFoundException("can not find drone with id".concat(id.toString()));
        return dataService.getMedicationRepo().findById(id).orElseThrow(() -> enfe);
    }

    @Override
    public Page<Medication> readAll(Integer pageNumber, Integer size) {
        PageRequest of = PageRequest.of(--pageNumber, size, Sort.by(Sort.Direction.DESC, "dateCreated"));

        return dataService.getMedicationRepo().findAll(of);
    }

    @Override
    public void delete(Long id) {
        dataService.getMedicationRepo().deleteById(id);
    }

    @Operation(hidden = true)
    @Override
    public void update(Medication request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
