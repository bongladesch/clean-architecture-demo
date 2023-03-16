package com.bongladesch.service;

import com.bongladesch.domain.EmailAddress;
import com.bongladesch.domain.Person;
import com.bongladesch.service.dto.EmailAddressDTO;
import com.bongladesch.service.exceptions.DataNotFoundException;
import com.bongladesch.service.exceptions.InvalidEmailException;
import com.bongladesch.service.interfaces.IEmailValidator;
import com.bongladesch.service.interfaces.IEventProducer;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AddressBookService {

  private static final Logger LOG = Logger.getLogger(AddressBookService.class);

  private final IEmailValidator emailValidator;
  private final IEventProducer eventProducer;

  @Inject
  public AddressBookService(IEmailValidator emailValidator, IEventProducer eventProducer) {
    this.emailValidator = emailValidator;
    this.eventProducer = eventProducer;
  }

  public Person createPerson(Person person) {
    LOG.infof("Create person with name: %s %s", person.firstName, person.lastName);
    person.persistPerson();
    return person;
  }

  public Person findPersonById(String id) {
    LOG.infof("Search for person with id: %s", id);
    return Person.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find person with id %s".formatted(id)));
  }

  public List<Person> listPersons() {
    LOG.info("List all persons sorted by lastName");
    return Person.listSortedByLastName();
  }

  public EmailAddress createEmailAddress(EmailAddressDTO emailAddressDTO) {
    LOG.infof("Create email %s for person with id: %s", emailAddressDTO.email(), emailAddressDTO.personId());
    Person person = findPersonById(emailAddressDTO.personId());
    if(!emailValidator.isValidEmail(emailAddressDTO.email()))
      throw new InvalidEmailException("Email %s is not valid".formatted(emailAddressDTO.email()));
    EmailAddress emailAddress = new EmailAddress();
    emailAddress.email = emailAddressDTO.email();
    emailAddress.person = person;
    emailAddress.persistEmail();
    eventProducer.sendEmailCreatedEvent(emailAddress.email);
    return emailAddress;
  }

  public EmailAddress findEmailAddress(String email) {
    LOG.infof("Search for email address: %s", email);
    return EmailAddress.findByEmail(email).orElseThrow(() -> new DataNotFoundException("Cannot find email-address %s".formatted(email)));
  }

  public List<EmailAddress> listEmailAddresses() {
    LOG.info("List all email addresses");
    return EmailAddress.listSortedByEmail();
  }

  public List<EmailAddress> listEmailAddressesOfPerson(String personId) {
    LOG.info("List all email addresses");
    return EmailAddress.listOfPerson(personId);
  }
}
