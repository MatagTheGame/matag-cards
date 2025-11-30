package com.matag.cards

import com.matag.cards.CardUtils.isColorless
import com.matag.cards.CardUtils.isOfOnlyAnyOfTheColors
import com.matag.cards.properties.Color
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Set

@ExtendWith(SpringExtension::class)
@Import(CardsConfiguration::class)
class CardUtilsTest {
    @Autowired
    private val cards: Cards? = null

    @Test
    fun isColorless() {
        Assertions.assertThat(isColorless(cards!!.get("Bishop's Soldier")!!)).isFalse()
        Assertions.assertThat(isColorless(cards.get("Jousting Dummy")!!)).isTrue()
    }

    @Test
    fun isOnlyAnyOfTheColors() {
        Assertions.assertThat(
            isOfOnlyAnyOfTheColors(
                cards!!.get("Inspiring Captain")!!,
                Set.of<Color?>(Color.WHITE, Color.RED)
            )
        ).isTrue()
        Assertions.assertThat(
            isOfOnlyAnyOfTheColors(
                cards.get("Fiery Finish")!!,
                Set.of<Color?>(Color.WHITE, Color.RED)
            )
        ).isTrue()
        Assertions.assertThat(
            isOfOnlyAnyOfTheColors(
                cards.get("Inspiring Veteran")!!,
                Set.of<Color?>(Color.WHITE, Color.RED)
            )
        ).isTrue()
        Assertions.assertThat(
            isOfOnlyAnyOfTheColors(
                cards.get("Centaur Peacemaker")!!,
                Set.of<Color?>(Color.WHITE, Color.RED)
            )
        ).isFalse()
        Assertions.assertThat(
            isOfOnlyAnyOfTheColors(
                cards.get("Jousting Dummy")!!,
                Set.of<Color?>(Color.WHITE, Color.RED)
            )
        ).isFalse()
    }
}