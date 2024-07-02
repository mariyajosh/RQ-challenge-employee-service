package com.example.rqchallenge.model.web.request;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class CreateEmployeeRequest {
    @NotBlank
    private String name;
    @Min(value = 1)
    private String age;
    @NotBlank
    private String salary;
}
