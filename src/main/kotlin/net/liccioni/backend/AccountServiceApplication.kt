package net.liccioni.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import javax.persistence.*

@EnableJpaAuditing
@SpringBootApplication
class AccountServiceApplication

fun main(args: Array<String>) {
    runApplication<AccountServiceApplication>(*args)
}

data class TodoRequest(var task: String? = null)

interface TodoRepository : JpaRepository<Todo, Long>
