package com.wefin.wefin.controller;

import com.wefin.wefin.facade.PersonFacade;
import com.wefin.wefin.facade.dto.person.PersonDTO;
import com.wefin.wefin.facade.dto.person.PersonToCreateUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonFacade facade;

    public PersonController(
            PersonFacade facade
    ) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(
            @RequestBody PersonToCreateUpdateDTO dto
    ) {
        PersonDTO response = facade.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> findAll() {
        List<PersonDTO> response = facade.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findById(
            @PathVariable UUID id
    ) {
        PersonDTO response = facade.findById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) throws Exception {
        facade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonDTO> update(
            @PathVariable UUID id,
            @RequestBody PersonToCreateUpdateDTO dto
    ) {
        PersonDTO response = facade.update(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
