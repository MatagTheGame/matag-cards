package com.matag.linker

import com.fasterxml.jackson.databind.ObjectMapper
import com.matag.cards.ResourceLoader
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class LinkerCards(val objectMapper: ObjectMapper, resourceLoader: ResourceLoader) {
    val cardsMap: Map<String, LinkerCard> = resourceLoader.getCardsFileNames()
        .map { parseCard(it) }
        .associateBy { it.name }

    fun all(): Collection<LinkerCard> =
        cardsMap.values

    private fun parseCard(resource: Resource): LinkerCard =
        try {
            objectMapper.readValue(resource.inputStream, LinkerCard::class.java)
        } catch (e: Exception) {
            throw Exception("Failed to load card: " + resource.description, e)
        }
}
