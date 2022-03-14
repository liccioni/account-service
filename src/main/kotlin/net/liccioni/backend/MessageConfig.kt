package net.liccioni.backend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessageConfig {

    @Bean
    fun personEventListener(): (BaseEntity) -> Unit {
        return { it -> println(it) }
    }
}