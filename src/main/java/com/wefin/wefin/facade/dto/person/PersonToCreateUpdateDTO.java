package com.wefin.wefin.facade.dto.person;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PersonToCreateUpdateDTO {

    private String name;

    private String identifier;

    private LocalDate birthDate;
}
