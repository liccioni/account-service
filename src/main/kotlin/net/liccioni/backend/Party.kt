package net.liccioni.backend

import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class Party(
    @Column(nullable = false, updatable = false, unique = true)
    val identifier: String,
) : BaseEntity()