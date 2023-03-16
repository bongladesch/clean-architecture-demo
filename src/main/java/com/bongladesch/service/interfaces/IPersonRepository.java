package com.bongladesch.service.interfaces;

import com.bongladesch.domain.Person;

import java.util.List;
import java.util.Optional;

public interface IPersonRepository {

  void persistPerson(Person person);

  Optional<Person> getById(String id);

  List<Person> listSortedByLastName();
}
