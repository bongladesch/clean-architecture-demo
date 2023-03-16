package com.bongladesch.adapter.postgres;

import com.bongladesch.domain.Person;
import com.bongladesch.service.exceptions.DataDuplicationException;
import com.bongladesch.service.interfaces.IPersonRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PersonRepository implements IPersonRepository, PanacheRepositoryBase<Person, String> {

  @Override
  public void persistPerson(Person person) throws DataDuplicationException {
    try {
      persistAndFlush(person);
    } catch (PersistenceException e) {
      throw new DataDuplicationException(e.getCause().getCause().getMessage());
    }
  }

  @Override
  public Optional<Person> getById(String id) {
    return findByIdOptional(id);
  }

  @Override
  public List<Person> listSortedByLastName() {
    return findAll(Sort.by("lastName")).list();
  }
}
