package com.bongladesch.service.interfaces;

import com.bongladesch.domain.EmailAddress;
import com.bongladesch.service.exceptions.DataDuplicationException;

import java.util.List;
import java.util.Optional;

public interface IEmailAddressRepository {

  void persistEmail(EmailAddress emailAddress) throws DataDuplicationException;

  Optional<EmailAddress> findByEmail(String email);

  List<EmailAddress> listSortedByEmail();

  List<EmailAddress> listOfPerson(String personId);
}
