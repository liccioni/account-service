package net.liccioni.backend

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.data.rest.core.annotation.HandleAfterCreate
import org.springframework.data.rest.core.annotation.RepositoryEventHandler
import org.springframework.integration.support.MessageBuilder
import org.springframework.stereotype.Component


@Component
@RepositoryEventHandler
class TodoEventHandler {

    private val logger: Logger = LogManager.getLogger(TodoEventHandler::class.java)

    @Autowired
    private lateinit var streamBridgeProvider: StreamBridge

    @HandleAfterCreate
    fun handleAuthorAfterCreate(todo: Todo) {
        logger.info(todo.task)
        streamBridgeProvider.send("orders-event-output", MessageBuilder.withPayload(todo).build())
    }
}