package com.wefin.wefin.facade.mapper;


import com.wefin.wefin.domain.Person;
import com.wefin.wefin.facade.dto.person.PersonDTO;
import com.wefin.wefin.facade.dto.person.PersonToCreateUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    Person personDtoToEntity(PersonDTO dto);

    Person personToCreateUpdateDtoToPerson(PersonToCreateUpdateDTO dto);

    PersonDTO personToDto(Person person);
}
