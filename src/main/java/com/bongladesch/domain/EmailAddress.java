package com.bongladesch.domain;

import com.bongladesch.service.exceptions.DataDuplicationException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "email_addresses")
@NamedQuery(name = "EmailAddresses.listOfPerson",
    query = "select distinct e from EmailAddress e join e.person p where p.id = ?1 order by e.email")
public class EmailAddress extends PanacheEntityBase {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id")
  public String id;

  @Column(name = "email", nullable = false, unique = true)
  public String email;

  @JoinColumn(name = "person_id")
  @ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  public Person person;

  public void persistEmail() throws DataDuplicationException {
    try {
      persistAndFlush();
    } catch (PersistenceException e) {
      throw new DataDuplicationException(e.getCause().getCause().getMessage());
    }
  }

  public static Optional<EmailAddress> findByEmail(String email) {
    return find("email", email).singleResultOptional();
  }

  public static List<EmailAddress> listSortedByEmail() {
    return findAll(Sort.by("email")).list();
  }

  public static List<EmailAddress> listOfPerson(String personId) {
    return find("#EmailAddresses.listOfPerson", personId).list();
  }
}
