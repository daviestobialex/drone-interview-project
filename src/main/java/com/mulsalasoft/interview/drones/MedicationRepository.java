/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mulsalasoft.interview.drones;

import com.mulsalasoft.interview.drones.entities.Medication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author daviestobialex
 */
public interface MedicationRepository extends CrudRepository<Medication, Long> {

    Page<Medication> findAll(Pageable pageable);

}
