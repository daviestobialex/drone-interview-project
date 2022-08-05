/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mulsalasoft.interview.drones;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author daviestobialex
 */
@Service
public class DataService {

    @Getter
    private final DroneRepository droneRepo;
    @Getter
    private final MedicationRepository medicationRepo;

    @Autowired
    public DataService(DroneRepository droneRepo, MedicationRepository medicationRepo) {
        this.droneRepo = droneRepo;
        this.medicationRepo = medicationRepo;
    }

}
