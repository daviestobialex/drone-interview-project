/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mulsalasoft.interview.drones.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author daviestobialex
 */
@Data
@MappedSuperclass
public class MedicationData extends BaseResponse implements Serializable {

    @Pattern(regexp = "[\\w\\-\\_0-9]+", message = "medication name can only accept letters, numbers, hypeh(-), and underscore(_)")
    @Column(unique = true, nullable = false, length = 150)
    @NotBlank(message = "medidcation name can not be null or blank")
    protected String name;
    @Column(precision = 5)
    @NotNull(message = "medidcation weight can not be null")
    protected Double weight;
    @NotBlank(message = "medidcation code can not be null or blank")
    @Size(max = 50, message = "medication code can not exceed 50 characters")
    @Column(unique = true, nullable = false, length = 50)
    @Pattern(regexp = "^[\\_A-Z0-9]*$", message = "medication code can only accept upper case letters, underscore(_) and numbers")
    protected String code;
    /**
     * this holds only a part of the image path which is dynamic and the rest is
     * built in the entity class
     */
    @Lob
    protected String imageId;
}
