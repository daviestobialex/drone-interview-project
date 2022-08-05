/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mulsalasoft.interview.drones.entities;

import com.mulsalasoft.interview.drones.models.DroneData;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author daviestobialex
 */
@Entity
@Data
@Schema
public class Drone extends DroneData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @Schema(hidden = true)
    @OneToMany
    private List<Medication> medications;

    @Schema(hidden = true)
    @Column(insertable = false, updatable = true)
    @UpdateTimestamp
    private Timestamp lastModified;

    @Schema(hidden = true)
    @CreationTimestamp
    private Timestamp dateCreated;

}
