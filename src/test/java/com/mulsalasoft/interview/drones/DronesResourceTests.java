package com.mulsalasoft.interview.drones;

import com.mulsalasoft.interview.drones.entities.Drone;
import com.mulsalasoft.interview.drones.models.DeployRequest;
import com.mulsalasoft.interview.drones.models.enums.DroneModel;
import com.mulsalasoft.interview.drones.models.enums.DroneState;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
class DronesResourceTests {

    @Autowired
    DroneResourceController controller;

    @Autowired
    DroneRepository repositiry;

    @Test
    void testCreatePass() {
        Assert.isTrue(repositiry.count() == 4, "post construct load data did not load the data");
        Drone request = new Drone();
        request.setBatteryPercentage(100);
        request.setModel(DroneModel.CRUISE);
        request.setState(DroneState.LOADING);
        request.setWeight(100.50);
        request.setSerialNumber("DRD2-005");

        controller.create(request);

        Assert.isTrue(repositiry.count() == 5, "persisting new drone failed");
    }

    @Test
    void testCreateFailed() {
        DataIntegrityViolationException thrown = assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    Assert.isTrue(repositiry.count() == 4, "post construct load data did not load the data");
                    Drone request = new Drone();
                    request.setBatteryPercentage(100);
                    request.setModel(DroneModel.CRUISE);
                    request.setState(DroneState.IDLE);
                    request.setWeight(100.50);
                    request.setSerialNumber("DRD2-003");

                    controller.create(request);
                },
                "Expected to throw DataIntegrityViolationException, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("could not execute statement"));

    }

    @Test
    void testReadPass() {
        Drone droneRecord = controller.read(2L);
        Assert.isTrue(droneRecord.getSerialNumber().equalsIgnoreCase("DRD2-002"), "could not read DRD2-002");
    }

    @Test
    void testReadFailed() {
        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> {
                    Drone droneRecord = controller.read(8L);
                });

        assertTrue(thrown.getMessage().contains("can not find drone with id"));
    }

    @Test
    void testReadAll() {
        Page<Drone> droneRecords = controller.readAll(1, 10);
        Assert.isTrue(droneRecords.getNumberOfElements() == 5, "Number of records have to be 5 it is currently " + droneRecords.getNumberOfElements());
    }

    @Test
    void testReadAllByState() {
        Page<Drone> droneRecords = controller.readAllByState(1, 10, DroneState.DELIVERED);
        Assert.isTrue(droneRecords.getNumberOfElements() == 1, "Number of records have to be 1 it is currently " + droneRecords.getNumberOfElements());

        droneRecords = controller.readAllByState(1, 10, DroneState.DELIVERING);
        Assert.isTrue(droneRecords.getNumberOfElements() == 1, "Number of records have to be 1 it is currently " + droneRecords.getNumberOfElements());

        droneRecords = controller.readAllByState(1, 10, DroneState.IDLE);
        Assert.isTrue(droneRecords.getNumberOfElements() == 1, "Number of records have to be 1 it is currently " + droneRecords.getNumberOfElements());

        droneRecords = controller.readAllByState(1, 10, DroneState.LOADING);
        Assert.isTrue(droneRecords.getNumberOfElements() == 1, "Number of records have to be 1 it is currently " + droneRecords.getNumberOfElements());
    }

    @Test
    void testUpdatePass() {
        Assert.isTrue(repositiry.findById(1L).get().getModel().equals(DroneModel.CRUISE), "first index not in cruise mode");
        Drone request = new Drone();
        request.setId(1L);
        request.setState(DroneState.RETURNING);
        request.setModel(DroneModel.MIDDLE);
        controller.update(request);
        Assert.isTrue(repositiry.findById(1L).get().getModel().equals(DroneModel.MIDDLE), "first index not chnaged to middle");
    }

    @Test
    void testDeletePass() {

        Assert.isTrue(repositiry.count() == 5, "drone count should be 5");

        Drone request = new Drone();
        request.setBatteryPercentage(100);
        request.setModel(DroneModel.CRUISE);
        request.setState(DroneState.LOADING);
        request.setWeight(100.50);
        request.setSerialNumber("DRD2-006");

        controller.create(request);

        Assert.isTrue(repositiry.count() == 6, "drone count should be 6");

        controller.delete(6L);

        Assert.isTrue(repositiry.count() == 5, "drone count should be 5");
    }

    @Test
    void testDeployFail() {
        InvalidDataAccessApiUsageException thrown = assertThrows(
                InvalidDataAccessApiUsageException.class,
                () -> {
                    Assert.isTrue(repositiry.count() == 5, "drone count should be 5");
                    DeployRequest request = new DeployRequest();
                    request.setDroneId(1L);
                    request.setMedicationIds(null);
                    controller.loadDrone(request);
                });
        assertTrue(thrown.getMessage().contains("must not be null"));
    }
}
