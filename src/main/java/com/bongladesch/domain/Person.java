package com.bongladesch.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "persons", uniqueConstraints = {@UniqueConstraint(columnNames = {"first_name", "last_name"})})
public class Person {

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
}
