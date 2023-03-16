package com.bongladesch.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

import javax.validation.constraints.NotBlank;

@RegisterForReflection
public record EmailAddressJSON(
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) String id,
    @NotBlank(message = "PersonId may not be blank") String personId,
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) String personFirstName,
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) String personLastName,
    @NotBlank(message = "Email may not be blank") String email) {
}
