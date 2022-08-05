/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mulsalasoft.interview.drones;

import javax.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author daviestobialex
 * @param <O> object to be presented
 */
public interface ControllerStrategy<O> {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "create",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void create(@RequestBody O request);

    @GetMapping(path = "read/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    O read(@PathVariable Long id);

    @GetMapping(path = "read/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    Page<O> readAll(@RequestParam @Min(value = 0, message = "page number can not be less than 0") Integer pageNumber,
            @Min(value = 0, message = "size can not be less than 0") @RequestParam Integer size);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(path = "update",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void update(@RequestBody O request);

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "delete")
    void delete(@PathVariable Long id);
    
     /**
     * this function pre-loads the database with sample drones and medications data
     */
    public void loadData();
}
