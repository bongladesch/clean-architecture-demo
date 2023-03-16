package com.bongladesch.controller;

import com.bongladesch.service.exceptions.DataDuplicationException;
import com.bongladesch.service.exceptions.DataNotFoundException;
import com.bongladesch.service.exceptions.InvalidEmailException;
import com.bongladesch.service.exceptions.RemoteAPIException;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.ws.rs.core.Response;

class ExceptionHandler {

  private static final Logger LOG = Logger.getLogger(ExceptionHandler.class);

  @ServerExceptionMapper
  public Response mapException(DataNotFoundException e) {
    LOG.info(e.getMessage());
    return Response.status(Response.Status.NOT_FOUND).entity(new ErrorJSON(e.getMessage())).build();
  }

  @ServerExceptionMapper
  public Response mapException(DataDuplicationException e) {
    return Response.status(Response.Status.CONFLICT).entity(new ErrorJSON(e.getMessage())).build();
  }

  @ServerExceptionMapper
  public Response mapException(InvalidEmailException e) {
    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorJSON(e.getMessage())).build();
  }

  @ServerExceptionMapper
  public Response mapException(RemoteAPIException e) {
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorJSON(e.getMessage())).build();
  }

  @ServerExceptionMapper
  public Response mapException(Exception e) {
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorJSON(e.getMessage())).build();
  }
}

@RegisterForReflection
record ErrorJSON(String message) {}
