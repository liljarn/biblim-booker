package ru.liljvrn.biblimbooker.support.configurations

import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import ru.liljvrn.biblimbooker.support.converters.GenreReadingConverter

@Configuration
class JdbcConfiguration(
    private val genreReadingConverter: GenreReadingConverter
) : AbstractJdbcConfiguration() {
    override fun userConverters(): List<Converter<*, *>> {
        return listOf(genreReadingConverter)
    }
}
