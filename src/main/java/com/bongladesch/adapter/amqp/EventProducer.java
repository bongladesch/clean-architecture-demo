package com.bongladesch.adapter.amqp;

import com.bongladesch.service.interfaces.IEventProducer;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EventProducer implements IEventProducer {

  private static final Logger LOG = Logger.getLogger(EventProducer.class);

  @Channel("emails")
  Emitter<String> emitter;

  @Override
  public void sendEmailCreatedEvent(String email) {
    LOG.infof("Send out email creation event: %s", email);
    emitter.send("Created email %s".formatted(email));
  }
}
