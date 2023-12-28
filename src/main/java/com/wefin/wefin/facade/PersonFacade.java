package com.wefin.wefin.facade;

import com.wefin.wefin.domain.Person;
import com.wefin.wefin.facade.dto.person.PersonDTO;
import com.wefin.wefin.facade.dto.person.PersonToCreateUpdateDTO;
import com.wefin.wefin.facade.mapper.PersonMapper;
import com.wefin.wefin.service.PersonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PersonFacade {

    private final PersonMapper mapper;
    private final PersonService service;

    public PersonFacade(
            PersonMapper mapper,
            PersonService service
    ) {
        this.mapper = mapper;
        this.service = service;
    }

    @Transactional
    public PersonDTO create(PersonToCreateUpdateDTO personToCreateUpdateDTO) {
        Person person = mapper.toEntity(personToCreateUpdateDTO);
        return mapper.toDto(service.create(person));
    }

    @Transactional(readOnly = true)
    public List<PersonDTO> findAll() {
        return service.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void delete(UUID id) throws Exception {
        service.delete(id);
    }

    @Transactional
    public PersonDTO update(UUID id, PersonToCreateUpdateDTO personToCreateUpdateDTO) {
        Person person = mapper.toEntity(personToCreateUpdateDTO);
        return mapper.toDto(service.update(id, person));
    }

    @Transactional(readOnly = true)
    public PersonDTO findById(UUID id) {
        return mapper.toDto(service.findById(id));
    }
}
