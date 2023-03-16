package com.bongladesch.controller.json;

import com.bongladesch.domain.EmailAddress;
import com.bongladesch.domain.Person;
import com.bongladesch.service.dto.EmailAddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface JsonMapper {

  // Persons
  PersonJSON fromDomain(Person person);

  Person toDomain(PersonJSON personJSON);

  List<PersonJSON> fromPersonDomainList(List<Person> persons);

  // Email addresses
  @Mapping(source = "person.id", target = "personId")
  @Mapping(source = "person.firstName", target = "personFirstName")
  @Mapping(source = "person.lastName", target = "personLastName")
  EmailAddressJSON fromDomain(EmailAddress emailAddress);

  EmailAddressDTO toDTO(EmailAddressJSON emailAddressJSON);

  List<EmailAddressJSON> fromEmailAddressDomainList(List<EmailAddress> emailAddresses);
}
