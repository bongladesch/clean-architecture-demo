package com.bongladesch.adapter.rapid;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@RegisterRestClient(configKey="email-validator-api")
public interface EmailValidatorClient {

  @GET
  Response validateEmailAddress(
      @HeaderParam("X-RapidAPI-Key") String apiKey,
      @HeaderParam("X-RapidAPI-Host") String apiHost,
      @QueryParam("domain") String email);
}
