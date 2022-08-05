/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mulsalasoft.interview.drones.entities;

import com.mulsalasoft.interview.drones.MedicationResourceController;
import com.mulsalasoft.interview.drones.models.MedicationData;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author daviestobialex
 */
@Entity
@Data
public class Medication extends MedicationData implements Serializable {

    @Schema(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(hidden = true)
    @Column(insertable = false, updatable = true)
    @UpdateTimestamp
    private Timestamp lastModified;

    @Schema(hidden = true)
    @CreationTimestamp
    private Timestamp dateCreated;

    @Transient
    public String getImagePath() {
        if (id == null || imageId == null) {
            return null;
        }

        return MedicationResourceController.MEDICATIONIMGS + id.toString().concat("/").concat(imageId);
    }

}
