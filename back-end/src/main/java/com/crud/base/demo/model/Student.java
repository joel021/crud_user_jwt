package com.crud.base.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
public class Student {

    @Pattern(regexp = "[0-9]{3}[.][0-9]{3}[.][0-9]{3}-[0-9]{2}")
    @Id
    private String register;

    @NotBlank(message = "You must provide the student name.")
    private String name;

    @NotNull(message = "You must provide the student register.")
    private Level level;

    @Min(message = "The min user score is 0.", value = 0)
    @Max(message = "The max user score is 10.", value = 10)
    private float score;

    @Email(message = "Provide a valid email.")
    private String email;
}
