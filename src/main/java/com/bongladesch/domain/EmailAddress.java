package com.bongladesch.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "email_addresses")
public class EmailAddress {

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
}
