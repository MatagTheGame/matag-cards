package com.matag.cards

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class Cards(val objectMapper: ObjectMapper, resourceLoader: ResourceLoader) {
    val cardsMap: Map<String, Card> = resourceLoader.getCardsFileNames()
        .map { parseCard(it) }
        .associateBy { it.name }

    fun all(): Collection<Card> =
        cardsMap.values

    fun get(name: String) =
        cardsMap[name] ?: throw Exception("Card $name not found!")

    private fun parseCard(resource: Resource): Card =
        try {
            objectMapper.readValue(resource.inputStream, Card::class.java)
        } catch (e: Exception) {
            throw Exception("Failed to load card: " + resource.description, e)
        }
}
