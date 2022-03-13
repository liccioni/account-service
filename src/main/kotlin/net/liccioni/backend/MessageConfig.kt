package net.liccioni.backend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder

@Configuration
class MessageConfig {

    @Bean
    fun orderEventListener(): (String) -> Message<String>? {
        return { it -> println(it); null }
    }
}