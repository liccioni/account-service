package net.liccioni.backend

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.data.rest.core.annotation.HandleAfterCreate
import org.springframework.data.rest.core.annotation.HandleBeforeCreate
import org.springframework.data.rest.core.annotation.RepositoryEventHandler
import org.springframework.integration.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
@RepositoryEventHandler
class BaseEntityEventHandler(private val idGenerator: IdGenerator) {

    private val logger: Logger = LogManager.getLogger(BaseEntityEventHandler::class.java)

    @Autowired
    private lateinit var streamBridgeProvider: StreamBridge

    @HandleBeforeCreate
    fun handleBeforeCreate(party: Party) {
        party.identifier = idGenerator.generate()
        logger.info("BeforeCreate: ${party.javaClass.simpleName} with id: ${party.identifier}")
    }

    @HandleAfterCreate
    fun handleAfterCreate(party: Party) {
        logger.info("AfterCreate: ${party.javaClass.simpleName} with id: ${party.identifier}")
        streamBridgeProvider.send("persons-event-output", MessageBuilder.withPayload(party).build())
    }
}