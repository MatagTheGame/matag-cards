package com.matag.cards

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
@ComponentScan(basePackageClasses = [CardsConfiguration::class])
open class CardsConfiguration {
    @Bean
    open fun cardsObjectMapper(): ObjectMapper {
        return jacksonObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    companion object {
        fun getResourcesPath(): String = File("src/main/resources").absolutePath
    }
}
