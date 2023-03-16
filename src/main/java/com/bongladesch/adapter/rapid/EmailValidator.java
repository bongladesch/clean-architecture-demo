package com.bongladesch.adapter.rapid;

import com.bongladesch.adapter.rapid.json.ValidationJSON;
import com.bongladesch.service.exceptions.RemoteAPIException;
import com.bongladesch.service.interfaces.IEmailValidator;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@ApplicationScoped
public class EmailValidator implements IEmailValidator {

  private static final Logger LOG = Logger.getLogger(EmailValidator.class);

  @ConfigProperty(name = "clean-demo.email-validator-api.api-key")
  String apiKey;

  @RestClient
  EmailValidatorClient emailValidatorClient;

  @Override
  public boolean isValidEmail(String email) {
    LOG.infof("Validate Email %s against RapidAPI", email);
    String errorMessage = "Email validation request failed with status code: %d";
    try(Response response = emailValidatorClient.validateEmailAddress(apiKey, "mailcheck.p.rapidapi.com", email)) {
      if (response.getStatus() == 200) {
        ValidationJSON validationJSON = response.readEntity(ValidationJSON.class);
        return validationJSON.valid();
      }
      throw new RemoteAPIException(errorMessage.formatted(response.getStatus()));
    } catch (WebApplicationException e) {
      throw new RemoteAPIException(errorMessage.formatted(e.getResponse().getStatus()));
    }
  }
}
