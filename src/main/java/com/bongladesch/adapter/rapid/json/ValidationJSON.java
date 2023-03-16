package com.bongladesch.adapter.rapid.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.runtime.annotations.RegisterForReflection;

@JsonIgnoreProperties(ignoreUnknown = true)
@RegisterForReflection
public record ValidationJSON(
    boolean valid,
    boolean block,
    boolean disposable
) {
}
