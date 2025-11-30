package com.matag.cards

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Supplier

@Component
class Cards(objectMapper: ObjectMapper, resourceLoader: ResourceLoader) {
    val cardsMap: MutableMap<String?, Card?> = LinkedHashMap<String?, Card?>()

    init {
        val cardResources = resourceLoader.getCardsFileNames()
        for (cardResource in cardResources) {
            try {
                val card = objectMapper.readValue<Card>(cardResource!!.getInputStream(), Card::class.java)
                cardsMap.put(card.name, card)
            } catch (e: Exception) {
                throw RuntimeException("Failed to load card: " + cardResource!!.getDescription(), e)
            }
        }
    }

    val all: MutableList<Card?>
        get() = ArrayList<Card?>(cardsMap.values)

    fun get(name: String?): Card? {
        return Optional.ofNullable<Card?>(cardsMap.get(name))
            .orElseThrow<RuntimeException?>(Supplier { RuntimeException("Card " + name + " not found!") })
    }
}
