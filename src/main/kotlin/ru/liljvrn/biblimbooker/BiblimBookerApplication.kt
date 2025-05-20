package ru.liljvrn.biblimbooker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import ru.liljvrn.biblimbooker.infrastructure.properties.BookerKafkaProperties
import ru.liljvrn.biblimbooker.infrastructure.properties.QdrantProperties
import ru.liljvrn.biblimbooker.infrastructure.properties.S3Properties
import ru.liljvrn.biblimbooker.support.properties.InternalApiProperties
import ru.liljvrn.biblimbooker.support.properties.ManagementApiProperties
import ru.liljvrn.biblimbooker.support.properties.WebClientProperties

@SpringBootApplication
@EnableAsync
@EnableKafka
@EnableScheduling
@EnableTransactionManagement
@EnableWebMvc
@EnableConfigurationProperties(
    S3Properties::class,
    QdrantProperties::class,
    BookerKafkaProperties::class,
    ManagementApiProperties::class,
    InternalApiProperties::class,
    WebClientProperties::class
)
class BiblimBookerApplication

fun main(args: Array<String>) {
    runApplication<BiblimBookerApplication>(*args)
}
