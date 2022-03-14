package net.liccioni.backend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessageConfig {

    @Bean
    fun orderEventListener(): (Todo) -> Unit {
        return { it -> println(it) }
    }
}