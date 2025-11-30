package com.matag.cards.sets

import com.matag.cards.Cards
import com.matag.cards.CardsConfiguration
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@Import(CardsConfiguration::class)
class MtgSetsTest {
    @Autowired
    private val cards: Cards? = null

    @Autowired
    private val mtgSets: MtgSets? = null

    @Test
    fun shouldLoadAllSets() {
        Assertions.assertThat(mtgSets!!.sets).isNotEmpty()
    }

    @Test
    fun shouldLoadASet() {
        val m20 = mtgSets!!.getSet("M20")
        Assertions.assertThat(m20.code).isEqualTo("M20")
        Assertions.assertThat(m20.name).isEqualTo("Core Set 2020")
        Assertions.assertThat(m20.cards).contains("Bladebrand")
    }

    @Test
    fun countCards() {
        LOGGER.info("Num of Cards: " + cards!!.all.size)
        LOGGER.info("Cards by Colors: " + cards.all.groupingBy { it.colors }.eachCount())
        LOGGER.info("Cards by Types: " + cards.all.groupingBy { it.types }.eachCount())
        LOGGER.info("Cards by Rarity: " + cards.all.groupingBy { it.rarity }.eachCount())
        LOGGER.info("Cards by Set: " + countCardsBySet(mtgSets!!.sets))
    }

    private fun countCardsBySet(sets: MutableMap<String?, MtgSet?>): MutableMap<String?, Int?> {
        val countCardsBySet = HashMap<String?, Int?>()

        for (setName in sets.keys) {
            countCardsBySet[setName] = sets[setName]!!.cards.size
        }

        return countCardsBySet
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(MtgSetsTest::class.java)
    }
}
