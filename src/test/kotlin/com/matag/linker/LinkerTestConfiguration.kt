package com.matag.linker

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.matag.cards.ResourceLoader
import com.matag.cards.sets.MtgSets
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class LinkerTestConfiguration {
    @Bean
    open fun cardsObjectMapper() =
        jacksonObjectMapper().apply {
            enable(SerializationFeature.INDENT_OUTPUT)
            setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT)
        }

    @Bean
    open fun setsObjectMapper() =
        jacksonObjectMapper().apply {
            enable(SerializationFeature.INDENT_OUTPUT)
            setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE)
            })
        }

    @Bean
    open fun resourceLoader() = ResourceLoader()

    @Bean
    open fun mtgSets(setsObjectMapper: ObjectMapper, resourceLoader: ResourceLoader) =
        MtgSets(setsObjectMapper, resourceLoader)

    @Bean
    open fun linkerCards(cardsObjectMapper: ObjectMapper, resourceLoader: ResourceLoader) =
        LinkerCards(cardsObjectMapper, resourceLoader)
}
