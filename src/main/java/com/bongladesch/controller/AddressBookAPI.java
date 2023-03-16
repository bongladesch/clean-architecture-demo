package com.bongladesch.controller;

import com.bongladesch.controller.json.EmailAddressJSON;
import com.bongladesch.controller.json.JsonMapper;
import com.bongladesch.controller.json.PersonJSON;
import com.bongladesch.service.AddressBookService;
import io.smallrye.common.constraint.NotNull;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Transactional
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api/v1")
public class AddressBookAPI {

  @Inject
  JsonMapper mapper;
  @Inject
  AddressBookService addressBookService;

  @POST
  @Path("/persons")
  public Response createPerson(@Valid @NotNull PersonJSON personJSON) {
    return Response.status(201).entity(mapper.fromDomain(addressBookService.createPerson(mapper.toDomain(personJSON)))).build();
  }

  @GET
  @Path("/persons/{id}")
  public Response listPersons(@PathParam("id") @NotNull String id) {
    return Response.ok(mapper.fromDomain(addressBookService.findPersonById(id))).build();
  }

  @GET
  @Path("/persons")
  public Response listPersons() {
    return Response.ok(mapper.fromPersonDomainList(addressBookService.listPersons())).build();
  }

  @POST
  @Path("/emails")
  public Response createEmailAddress(@Valid @NotNull EmailAddressJSON emailAddressJSON) {
    return Response.status(201).entity(mapper.fromDomain(addressBookService.createEmailAddress(mapper.toDTO(emailAddressJSON)))).build();
  }

  @GET
  @Path("/emails")
  public Response listAddresses() {
    return Response.ok(mapper.fromEmailAddressDomainList(addressBookService.listEmailAddresses())).build();
  }

  @GET
  @Path("/emails/search")
  public Response listEmailAddresses(@QueryParam("email") @NotNull String email) {
    return Response.ok(mapper.fromDomain(addressBookService.findEmailAddress(email))).build();
  }

  @GET
  @Path("/emails/person/{id}")
  public Response listEmailsAddressesOfPerson(@PathParam("id") String personId) {
    return Response.ok(mapper.fromEmailAddressDomainList(addressBookService.listEmailAddressesOfPerson(personId))).build();
  }
}
