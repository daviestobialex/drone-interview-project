/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mulsalasoft.interview.drones;

import com.mulsalasoft.interview.drones.entities.Drone;
import com.mulsalasoft.interview.drones.entities.Medication;
import com.mulsalasoft.interview.drones.models.BaseResponse;
import com.mulsalasoft.interview.drones.models.DeployRequest;
import com.mulsalasoft.interview.drones.models.enums.DroneState;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daviestobialex
 */
@RestController("drone")
public class DroneResourceController implements ControllerStrategy<Drone> {

    private final DataService dataService;

    @Autowired
    public DroneResourceController(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void create(Drone request) {
        dataService.getDroneRepo().save(request);
    }

    @Override
    public Drone read(Long id) {
        EntityNotFoundException enfe = new EntityNotFoundException("can not find drone with id".concat(id.toString()));
        return dataService.getDroneRepo().findById(id).orElseThrow(() -> enfe);
    }

    @Override
    public Page<Drone> readAll(Integer pageNumber, Integer size) {
        PageRequest of = PageRequest.of(--pageNumber, size, Sort.by(Sort.Direction.DESC, "dateCreated"));

        return dataService.getDroneRepo().findAll(of);
    }

    @GetMapping(path = "read/search",
            produces = MediaType.APPLICATION_JSON_VALUE)

    public Page<Drone> readAllByState(
            @RequestParam @Min(value = 0, message = "page number can not be less than 0") Integer pageNumber,
            @RequestParam @Min(value = 0, message = "size can not be less than 0") Integer size,
            @RequestParam DroneState state) {
        PageRequest of = PageRequest.of(--pageNumber, size, Sort.by(Sort.Direction.DESC, "dateCreated"));

        return dataService.getDroneRepo().findAllByState(of, state);
    }

    @Operation(summary = "updates a drone", description = "this simply updates a drones' model and state. Based on the scope, this resource would be used to communicate specifically to the drones and dispatch commands based on the defined drone state")
    @Override
    public void update(Drone request) {
        EntityNotFoundException enfe = new EntityNotFoundException("can not find drone with id".concat(request.getId().toString()));
        Drone droneRecord = dataService.getDroneRepo().findById(request.getId()).orElseThrow(() -> enfe);
        droneRecord.setModel(request.getModel());
        droneRecord.setState(request.getState());
        dataService.getDroneRepo().save(droneRecord);
    }

    @Override
    public void delete(Long id) {
        dataService.getDroneRepo().deleteById(id);
    }

    /**
     * this assigns a list of medications to a drone for delivery
     *
     * @param request
     * @return
     */
    @Operation(summary = "loads a drone", description = "this simply ties persisted medications to a persissted drone. this resource would be used to communicate specifically to the drones or inidivuals/systems assigned to load a drone with the required medication")
    @PostMapping(path = "load",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse loadDrone(@RequestBody DeployRequest request) {

        EntityNotFoundException enfe = new EntityNotFoundException("can not find drone with id".concat(request.getDroneId().toString()));
        Drone droneRecord = dataService.getDroneRepo().findById(request.getDroneId()).orElseThrow(() -> enfe);

        if (droneRecord.getBatteryPercentage() > 25) {
            List<Medication> medications = (List<Medication>) dataService.getMedicationRepo().findAllById(request.getMedicationIds());

            droneRecord.setMedications(medications);
            droneRecord.setState(DroneState.LOADING);

            dataService.getDroneRepo().save(droneRecord);

            return new BaseResponse("drone deployed successfully");
        }
        return new BaseResponse("battery low, drone can not be deployed");

    }
}
