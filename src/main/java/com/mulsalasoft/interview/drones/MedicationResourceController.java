/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mulsalasoft.interview.drones;

import com.mulsalasoft.interview.drones.entities.Medication;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author daviestobialex
 */
@Slf4j
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
        EntityNotFoundException enfe = new EntityNotFoundException("can not find medication with id".concat(id.toString()));
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

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    @PutMapping(path = "/picture")
    public void setPicture(@RequestParam("imageFile") MultipartFile file, @RequestParam Long id) throws IOException {

        Medication medicationRecord = read(id);

        String buildid = "-".concat(Integer.toString(new SecureRandom().nextInt(99))).concat(".").concat(FilenameUtils.getExtension(file.getOriginalFilename()));

        medicationRecord.setImageId(buildid);

        dataService.getMedicationRepo().save(medicationRecord);

        File f = new File(MEDICATIONIMGS);
        if (!f.exists()) {
            f.mkdir();
        }

        String uploadDir = MEDICATIONIMGS + medicationRecord.getId();
        String fileName = "medicine-image";
        log.info(" upload dir : {}, file : {}, ", uploadDir, fileName);

        saveFile(uploadDir, fileName, file);

    }
    public static final String MEDICATIONIMGS = "medication-imgs/";

    protected void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try ( InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    @Override
    public void loadData() {
        Medication medicationRecord = new Medication();
        medicationRecord.setCode("MED-001");
        medicationRecord.setImageId(null);
        medicationRecord.setName("Paracetamol");
        medicationRecord.setWeight(10.00);

        dataService.getMedicationRepo().save(medicationRecord);

        medicationRecord = new Medication();
        medicationRecord.setCode("MED-002");
        medicationRecord.setImageId(null);
        medicationRecord.setName("VitaminC");
        medicationRecord.setWeight(15.00);

        dataService.getMedicationRepo().save(medicationRecord);

        medicationRecord = new Medication();
        medicationRecord.setCode("MED-003");
        medicationRecord.setImageId(null);
        medicationRecord.setName("Anti-botics");
        medicationRecord.setWeight(20.00);

        dataService.getMedicationRepo().save(medicationRecord);
    }

}
