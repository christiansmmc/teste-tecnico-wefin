package com.wefin.wefin.service;

import com.wefin.wefin.domain.Person;

import java.util.List;
import java.util.UUID;

public interface PersonService {

    Person create(Person person);

    List<Person> findAll();

    void delete(UUID id) throws Exception;

    Person update(UUID id, Person person);

    Person findById(UUID id);
}
