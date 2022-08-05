/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mulsalasoft.interview.drones;

import com.mulsalasoft.interview.drones.entities.Drone;
import com.mulsalasoft.interview.drones.models.enums.DroneState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author daviestobialex
 */
@Repository
public interface DroneRepository extends CrudRepository<Drone, Long> {

    Page<Drone> findAll(Pageable pageable);

    public Page<Drone> findAllByState(PageRequest of, DroneState state);
}
