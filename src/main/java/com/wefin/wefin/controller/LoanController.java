package com.wefin.wefin.controller;

import com.wefin.wefin.facade.LoanFacade;
import com.wefin.wefin.facade.dto.loan.LoanDTO;
import com.wefin.wefin.facade.dto.loan.LoanToCreateDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanFacade facade;

    @Autowired
    public LoanController(
            LoanFacade facade
    ) {
        this.facade = facade;
    }

    @PostMapping
    public ResponseEntity<LoanDTO> create(
            @RequestBody @Valid LoanToCreateDTO dto
    ) {
        LoanDTO response = facade.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
