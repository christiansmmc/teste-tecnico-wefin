package com.wefin.wefin.controller;

import com.wefin.wefin.BaseAbstractIntegrationTest;
import com.wefin.wefin.domain.Person;
import com.wefin.wefin.domain.enumeration.IdentifierType;
import com.wefin.wefin.facade.dto.person.PersonToCreateUpdateDTO;
import com.wefin.wefin.util.StringUtils;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PersonControllerIT extends BaseAbstractIntegrationTest {

    private static final String URL = "/api/persons";

    Faker faker = new Faker();

    @Test
    public void create() throws Exception {
        PersonToCreateUpdateDTO dto = PersonToCreateUpdateDTO
                .builder()
                .name(faker.name().fullName())
                .identifier(faker.cpf().valid())
                .birthDate(LocalDate.now())
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.identifierType").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.identifierType").value(IdentifierType.PF.name()));
    }

    @Test
    public void findAll() throws Exception {
        Person person = Person
                .builder()
                .name(faker.name().fullName())
                .identifier(StringUtils.removeAllExceptNumber(faker.cpf().valid()))
                .birthDate(LocalDate.now())
                .minInstallmentValue(BigDecimal.TEN)
                .maxLoanValue(BigDecimal.TEN)
                .identifierType(IdentifierType.PF)
                .build();

        Person personCrated = personRepository.saveAndFlush(person);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(personCrated.getId().toString()));
    }

    @Test
    public void findById() throws Exception {
        Person person = Person
                .builder()
                .name(faker.name().fullName())
                .identifier(StringUtils.removeAllExceptNumber(faker.cpf().valid()))
                .birthDate(LocalDate.now())
                .minInstallmentValue(BigDecimal.TEN)
                .maxLoanValue(BigDecimal.TEN)
                .identifierType(IdentifierType.PF)
                .build();

        Person personCrated = personRepository.saveAndFlush(person);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/" + personCrated.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(personCrated.getId().toString()));
    }

    @Test
    public void delete() throws Exception {
        Person person = Person
                .builder()
                .name(faker.name().fullName())
                .identifier(StringUtils.removeAllExceptNumber(faker.cpf().valid()))
                .birthDate(LocalDate.now())
                .minInstallmentValue(BigDecimal.TEN)
                .maxLoanValue(BigDecimal.TEN)
                .identifierType(IdentifierType.PF)
                .build();

        Person personCrated = personRepository.saveAndFlush(person);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/" + personCrated.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
