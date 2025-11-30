package com.matag.cards

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.matag.cards.properties.Type
import com.matag.cards.sets.MtgSets
import com.matag.downloader.CardScryFallLinker
import org.junit.Ignore
import org.junit.Test
import org.junit.platform.commons.util.StringUtils
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner
import java.nio.file.Files
import java.nio.file.Paths

@RunWith(SpringRunner::class)
@Import(CardsConfiguration::class)
class LinkerTest {
    @Autowired
    private val cards: Cards? = null

    @Autowired
    private val mtgSets: MtgSets? = null

    @Ignore
    @Test
    @Throws(Exception::class)
    fun scryFallLinker() {
        val cardsObjectMapper = createCardsObjectMapper()
        val setsObjectMapper = createSetsObjectMapper()

        val sets = mtgSets!!.getSets()

        val cardsToLink = cards!!.getAll().stream()
            .filter { card: Card? -> StringUtils.isBlank(card!!.imageUrl) }
            .toList()

        for (i in cardsToLink.indices) {
            val card: Card = cardsToLink.get(i)
            val cardScryFallLinker = CardScryFallLinker(card)
            val updatedCard = card.copy(
                    imageUrl = cardScryFallLinker.getImage(),
                    types = cardScryFallLinker.getTypes(),
                    subtypes = cardScryFallLinker.getSubtypes(),
                    power = cardScryFallLinker.getPower(),
                    toughness = cardScryFallLinker.getToughness(),
                    rarity = cardScryFallLinker.getRarity(),
                    ruleText = cardScryFallLinker.getOracleText(),
                    colors = cardScryFallLinker.getColors(),
                    cost = cardScryFallLinker.getManaCost()
                )
            val cardJson = cardsObjectMapper.writeValueAsString(updatedCard)
            Files.writeString(
                Paths.get(CardsConfiguration.getResourcesPath() + "/cards/" + card.name + ".json"),
                cardJson
            )
            println("Downloaded " + (i + 1) + " of " + cardsToLink.size + " -> " + card.name)

            if (!card.types!!.contains(Type.BASIC)) {
                for (scryFallSet in cardScryFallLinker.getSets()) {
                    if (sets.containsKey(scryFallSet)) {
                        sets.get(scryFallSet)!!.getCards().add(card.name)
                        val setJson = setsObjectMapper.writeValueAsString(sets.get(scryFallSet))
                        Files.writeString(
                            Paths.get(CardsConfiguration.getResourcesPath() + "/sets/" + scryFallSet + ".json"),
                            setJson
                        )
                    }
                }
            }
        }
    }

    fun createCardsObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT)
        return objectMapper
    }

    fun createSetsObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
        val prettyPrinter = DefaultPrettyPrinter()
        prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE)
        objectMapper.setDefaultPrettyPrinter(prettyPrinter)
        return objectMapper
    }
}
