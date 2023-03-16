package com.bongladesch.domain;

import com.bongladesch.service.exceptions.DataDuplicationException;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "persons", uniqueConstraints = {@UniqueConstraint(columnNames = {"first_name", "last_name"})})
public class Person extends PanacheEntityBase {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id")
  public String id;

  @Column(name = "first_name", nullable = false)
  public String firstName;

  @Column(name = "last_name", nullable = false)
  public String lastName;

  @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
  public Set<EmailAddress> emailAddresses;

  public void persistPerson() throws DataDuplicationException {
    try {
      persistAndFlush();
    } catch (PersistenceException e) {
      throw new DataDuplicationException(e.getCause().getCause().getMessage());
    }
  }

  public static Optional<Person> findById(String id) {
    return findByIdOptional(id);
  }

  public static List<Person> listSortedByLastName() {
    return findAll(Sort.by("lastName")).list();
  }
}
