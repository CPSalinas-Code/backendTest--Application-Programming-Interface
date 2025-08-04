package com.company.customerperson.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@Data
public class PersonDTO {
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    //@NotBlank(message = "El género es obligatorio")
    private String gender;

    //@NotNull(message = "La edad es obligatoria")
    @Min(value = 1, message = "La edad debe ser mayor a 0")
    private Integer age;

    @NotBlank(message = "La identificación es obligatoria")
    private String identification;

    @NotBlank(message = "La dirección es obligatoria")
    private String address;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{9,10}$", message = "El teléfono debe contener solo números y tener 9 o 10 dígitos")
    private String phone;
}