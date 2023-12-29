package com.wefin.wefin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefin.wefin.repository.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(classes = WefinApplication.class)
public abstract class BaseAbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected RepositoryCleanerService repositoryCleanerService;
    @Autowired
    protected PersonRepository personRepository;

    @AfterEach
    public void cleanDatabase() {
        repositoryCleanerService.resetDatabase();
    }
}
