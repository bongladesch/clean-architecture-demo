package com.bongladesch.adapter.postgres;

import com.bongladesch.domain.EmailAddress;
import com.bongladesch.service.exceptions.DataDuplicationException;
import com.bongladesch.service.interfaces.IEmailAddressRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NamedQuery(name = "EmailAddresses.listOfPerson",
    query = "select distinct e from EmailAddress e join e.person p where p.id = ?1 order by e.email")
public class EmailAddressRepository implements IEmailAddressRepository, PanacheRepositoryBase<EmailAddress, String> {

  public void persistEmail(EmailAddress emailAddress) throws DataDuplicationException {
    try {
      persistAndFlush(emailAddress);
    } catch (PersistenceException e) {
      throw new DataDuplicationException(e.getCause().getCause().getMessage());
    }
  }

  public Optional<EmailAddress> findByEmail(String email) {
    return find("email", email).singleResultOptional();
  }

  public List<EmailAddress> listSortedByEmail() {
    return findAll(Sort.by("email")).list();
  }

  public List<EmailAddress> listOfPerson(String personId) {
    return find("#EmailAddresses.listOfPerson", personId).list();
  }
}
