package net.liccioni.backend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class AppConfig {

    @Bean
    fun idGenerator(): IdGenerator {
        return object : IdGenerator {
            override fun generate(): String {
                return UUID.randomUUID().toString()
            }
        }
    }
}