package net.liccioni.backend

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "persons")
class Person(identifier: String) : Party(identifier)