package com.wefin.wefin.facade.dto.person;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PersonToCreateUpdateDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String identifier;

    @NotNull
    private LocalDate birthDate;
}
