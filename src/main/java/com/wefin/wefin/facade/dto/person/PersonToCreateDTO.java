package com.wefin.wefin.facade.dto.person;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PersonToCreateDTO {

    private String name;

    private String identifier;

    private LocalDate birthDate;
}
