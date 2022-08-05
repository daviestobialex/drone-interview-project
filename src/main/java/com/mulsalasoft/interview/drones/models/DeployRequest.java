/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mulsalasoft.interview.drones.models;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 *
 * @author daviestobialex
 */
@Data
public class DeployRequest implements Serializable {

    protected List<Long> medicationIds;
    protected Long droneId;
}
