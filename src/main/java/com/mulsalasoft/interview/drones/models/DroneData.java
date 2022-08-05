/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mulsalasoft.interview.drones.models;

import com.mulsalasoft.interview.drones.models.enums.DroneModel;
import com.mulsalasoft.interview.drones.models.enums.DroneState;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author daviestobialex
 */
@Data
@MappedSuperclass
public class DroneData extends BaseResponse implements Serializable {

    @Max(value = 100, message = "drone serial number can not exceed 100 characters")
    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "serial number can not be null or blank")
    protected String serialNumber;
    @Max(value = 500, message = "drone weight can not exceed 500 gr")
    @Column(precision = 5, nullable = false)
    @NotNull(message = "weight can not be null")
    protected Double weight;
    @NotNull(message = "battery percentage can not be null")
    @Max(value = 100, message = "drone battery percentage can not exceed 100 percent")
    protected Integer batteryPercentage;
    @NotNull(message = "drone model can not be null")
    protected DroneModel model;
    @NotNull(message = "drone state can not be null")
    protected DroneState state;

}
