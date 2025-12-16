package com.matag.linker

import com.fasterxml.jackson.databind.ObjectMapper
import com.matag.cards.CardsConfiguration
import com.matag.cards.properties.Type
import com.matag.cards.sets.MtgSets
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.platform.commons.util.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.nio.file.Files
import java.nio.file.Paths

@ExtendWith(SpringExtension::class)
@Import(LinkerTestConfiguration::class)
class LinkerTest(
    @param:Autowired val cardsObjectMapper: ObjectMapper,
    @param:Autowired val setsObjectMapper: ObjectMapper,
    @param:Autowired val linkerCards: LinkerCards,
    @param:Autowired val mtgSets: MtgSets
) {
    @Test
    @Throws(Exception::class)
    fun `link a card from scryFall`() {
        val sets = mtgSets.sets

        val cardsToLink = linkerCards.all()
            .filter { StringUtils.isBlank(it.imageUrl) }

        for ((i, card) in cardsToLink.withIndex()) {
            val cardScryFallLinker = CardScryFallLinker(card)
            val updatedCard = card.copy(
                imageUrl = cardScryFallLinker.image,
                types = cardScryFallLinker.types,
                subtypes = cardScryFallLinker.subtypes,
                power = cardScryFallLinker.power,
                toughness = cardScryFallLinker.toughness,
                rarity = cardScryFallLinker.rarity,
                ruleText = cardScryFallLinker.oracleText,
                colors = cardScryFallLinker.colors,
                cost = cardScryFallLinker.manaCost
            )
            val cardJson = cardsObjectMapper.writeValueAsString(updatedCard)
            save("/cards/${card.name}.json", cardJson)
            println("Downloaded ${i + 1} of ${cardsToLink.size} -> ${card.name}")

            if (updatedCard.types?.contains(Type.BASIC) == false) {
                for (scryFallSet in cardScryFallLinker.sets) {
                    if (sets.containsKey(scryFallSet)) {
                        sets[scryFallSet]!!.cards.add(card.name)
                        val setJson = setsObjectMapper.writeValueAsString(sets[scryFallSet])
                        save("/sets/${scryFallSet}.json", setJson)
                    }
                }
            }
        }
    }

    private fun save(path: String, cardJson: String) {
        Files.writeString(
            Paths.get(CardsConfiguration.getResourcesPath() + path),
            cardJson
        )
    }
}