package com.wefin.wefin.service.impl;

import com.wefin.wefin.repository.LoanRepository;
import com.wefin.wefin.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private LoanRepository repository;
}
