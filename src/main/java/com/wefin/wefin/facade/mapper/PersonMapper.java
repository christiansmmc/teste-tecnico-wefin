package com.wefin.wefin.facade.mapper;


import com.wefin.wefin.domain.Person;
import com.wefin.wefin.facade.dto.person.PersonDTO;
import com.wefin.wefin.facade.dto.person.PersonToCreateUpdateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person toEntity(PersonToCreateUpdateDTO dto);

    PersonDTO toDto(Person person);
}
