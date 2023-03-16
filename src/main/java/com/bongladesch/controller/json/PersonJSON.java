package com.bongladesch.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record PersonJSON(
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) String id,
    @NotBlank(message = "FirstName may not be blank") String firstName,
    @NotBlank(message = "LastName may not be blank") String lastName
) {
}
