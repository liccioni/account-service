package net.liccioni.backend

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.cloud.stream.test.binder.TestSupportBinder
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class AccountServiceApplicationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var testSupportBinder: TestSupportBinder

    @MockBean
    lateinit var idGenerator: IdGenerator

    companion object {
        @Container
        private val mysqlContainer = MySQLContainer("mysql:8.0.22")
                .withDatabaseName("myapp")

        @Container
        private val rabbitContainer = RabbitMQContainer("rabbitmq:3.9-alpine")

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {

            registry.add("spring.jpa.show-sql") { true }
            registry.add("spring.jpa.properties.hibernate.format_sql") { true }
            registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
            registry.add("spring.datasource.password", mysqlContainer::getPassword)
            registry.add("spring.datasource.username", mysqlContainer::getUsername)
            registry.add("spring.jpa.hibernate.ddl-auto") { "update" }

            registry.add("spring.rabbitmq.host", rabbitContainer::getContainerIpAddress)
            registry.add("spring.rabbitmq.port") { rabbitContainer.getMappedPort(5672) }
        }
    }

    @Test
    fun `should create a todo item`() {
        val identifier = "firstId"
        `when`(idGenerator.generate()).thenReturn(identifier)

        val json = objectMapper.writeValueAsString(Person(identifier))
        this.mockMvc.perform(
                post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated)

        val payload = testSupportBinder.messageCollector()
                .forChannel(testSupportBinder.getChannelForName("persons.topic")).poll().payload as String
        val actual = objectMapper.readValue<Person>(payload)
        assertThat(actual.identifier).isEqualTo(identifier)
    }

}
