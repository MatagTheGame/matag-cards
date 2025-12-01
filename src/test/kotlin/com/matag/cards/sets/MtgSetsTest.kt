package com.matag.cards.sets

import com.matag.cards.Cards
import com.matag.cards.CardsConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@Import(CardsConfiguration::class)
class MtgSetsTest(
    @param:Autowired val cards: Cards,
    @param:Autowired val mtgSets: MtgSets
) {

    @Test
    fun shouldLoadAllSets() {
        assertThat(mtgSets.sets).isNotEmpty()
    }

    @Test
    fun shouldLoadASet() {
        val m20 = mtgSets.get("M20")
        assertThat(m20.code).isEqualTo("M20")
        assertThat(m20.name).isEqualTo("Core Set 2020")
        assertThat(m20.cards).contains("Bladebrand")
    }

    @Test
    fun countCards() {
        LOGGER.info("Num of Cards: {}", cards.all().size)
        LOGGER.info("Cards by Colors {}", cards.all().groupingBy { it.colors }.eachCount())
        LOGGER.info("Cards by Types: {}", cards.all().groupingBy { it.types }.eachCount())
        LOGGER.info("Cards by Rarity: {}", cards.all().groupingBy { it.rarity }.eachCount())
        LOGGER.info("Cards by Set: {}", mtgSets.sets.mapValues { it.value.cards.size })
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(MtgSetsTest::class.java)
    }
}
