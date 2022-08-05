/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mulsalasoft.interview.drones;

import com.mulsalasoft.interview.drones.entities.Medication;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

/**
 *
 * @author daviestobialex
 */
@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class MedicationResourceTests {

    @Autowired
    MedicationResourceController controller;

    @Autowired
    MedicationRepository repositiry;

    @Test
    void testCreatePass() {
        Assert.isTrue(repositiry.count() == 3, "post construct load data did not load the data");
        Medication request = new Medication();
        request.setCode("MED_004");
        request.setName("fancida-cholride");
        request.setWeight(100.50);

        controller.create(request);

        Assert.isTrue(repositiry.count() == 4, "persisting new medication failed");
    }

    @Test
    void testCreateFailed() {
        DataIntegrityViolationException thrown = assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    Assert.isTrue(repositiry.count() == 3, "post construct load data did not load the data");
                    Medication request = new Medication();
                    request.setCode("MED_003");
                    request.setName("fancida-cholride");
                    request.setWeight(100.50);

                    controller.create(request);
                },
                "Expected to throw DataIntegrityViolationException, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("could not execute statement"));

    }

    @Test
    void testReadPass() {
        Medication medicationRecord = controller.read(2L);
        Assert.isTrue(medicationRecord.getCode().equalsIgnoreCase("MED_002"), "could not read MED_002");
    }

    @Test
    void testReadFailed() {
        EntityNotFoundException thrown = assertThrows(
                EntityNotFoundException.class,
                () -> {
                    Medication medicationRecord = controller.read(8L);
                });

        assertTrue(thrown.getMessage().contains("can not find medication with id"));
    }

    @Test
    void testReadAll() {
        Page<Medication> medicationRecords = controller.readAll(1, 10);
        Assert.isTrue(medicationRecords.getNumberOfElements() == 4, "Number of records have to be 4 it is currently " + medicationRecords.getNumberOfElements());
    }

    @Test
    void testDeletePass() {

        Assert.isTrue(repositiry.count() == 4, "medication count should be 4");

        Medication request = new Medication();
        request.setCode("MED_005");
        request.setName("ampiclause");
        request.setWeight(120.50);
        controller.create(request);

        Assert.isTrue(repositiry.count() == 5, "medication count should be 5");

        controller.delete(5L);

        Assert.isTrue(repositiry.count() == 4, "medication count should be 4");
    }
}
