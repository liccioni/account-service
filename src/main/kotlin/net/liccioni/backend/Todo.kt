package net.liccioni.backend

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "todos")
class Todo(var task: String) : BaseEntity()