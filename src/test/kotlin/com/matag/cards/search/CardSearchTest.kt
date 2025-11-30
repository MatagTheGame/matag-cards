package com.matag.cards.search

import com.matag.cards.Card
import com.matag.cards.Cards
import com.matag.cards.CardsConfiguration
import com.matag.cards.properties.Color
import com.matag.cards.properties.Subtype
import com.matag.cards.properties.Type
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit4.SpringRunner
import java.util.List
import java.util.Set

@RunWith(SpringRunner::class)
@Import(CardsConfiguration::class)
class CardSearchTest {
    @Autowired
    private val cards: Cards? = null

    @Test
    fun empty() {
        Assertions.assertThat(CardSearch(mutableListOf<Card?>()).isEmpty()).isTrue()
    }

    @Test
    fun notEmpty() {
        Assertions.assertThat(CardSearch(List.of<Card?>(cards!!.get("Plains"))).isNotEmpty()).isTrue()
    }

    @Test
    fun concat() {
        Assertions.assertThat<Card?>(
            CardSearch(List.of<Card?>(cards!!.get("Plains")))
                .concat(List.of<Card?>(cards.get("Mountain")))
                .getCards()
        )
            .hasSize(2)
    }

    @Test
    fun ofType() {
        // Given
        val cardSearch = CardSearch(List.of<Card?>(cards!!.get("Plains"), cards.get("Bedevil")))

        // When
        val result = cardSearch.ofType(Type.LAND).getCards()

        // Then
        Assertions.assertThat<Card?>(result).contains(cards.get("Plains"))
    }

    @Test
    fun notOfType() {
        // Given
        val cardSearch = CardSearch(List.of<Card?>(cards!!.get("Plains"), cards.get("Bedevil")))

        // When
        val result = cardSearch.notOfType(Type.LAND).getCards()

        // Then
        Assertions.assertThat<Card?>(result).contains(cards.get("Bedevil"))
    }

    @Test
    fun ofSubtype() {
        // Given
        val cardSearch = CardSearch(List.of<Card?>(cards!!.get("Plains"), cards.get("Dusk Legion Zealot")))

        // When
        val result = cardSearch.ofSubtype(Subtype.SOLDIER).getCards()

        // Then
        Assertions.assertThat<Card?>(result).contains(cards.get("Dusk Legion Zealot"))
    }

    @Test
    fun notOfSubtype() {
        // Given
        val cardSearch = CardSearch(List.of<Card?>(cards!!.get("Plains"), cards.get("Dusk Legion Zealot")))

        // When
        val result = cardSearch.notOfSubtype(Subtype.SOLDIER).getCards()

        // Then
        Assertions.assertThat<Card?>(result).contains(cards.get("Plains"))
    }

    @Test
    fun ofColor() {
        // Given
        val cardSearch = CardSearch(
            List.of<Card?>(
                cards!!.get("Empyrean Eagle"),  // white blue
                cards.get("Angel of the Dawn"),  // white
                cards.get("Dark Nourishment") // black
            )
        )

        // When
        val result = cardSearch.ofColor(Color.WHITE).getCards()

        // Then
        Assertions.assertThat<Card?>(result).contains(cards.get("Empyrean Eagle"), cards.get("Angel of the Dawn"))
    }

    @Test
    fun ofOnlyAnyOfTheColors() {
        // Given
        val cardSearch = CardSearch(
            List.of<Card?>(
                cards!!.get("Empyrean Eagle"),  // white blue
                cards.get("Angel of the Dawn"),  // white
                cards.get("Swiftblade Vindicator"),  // white red
                cards.get("Dark Nourishment") // black
            )
        )

        // When
        val result = cardSearch.ofOnlyAnyOfTheColors(Set.of<Color?>(Color.WHITE, Color.BLUE)).getCards()

        // Then
        Assertions.assertThat<Card?>(result).contains(cards.get("Empyrean Eagle"), cards.get("Angel of the Dawn"))
    }

    @Test
    fun colorless() {
        // Given
        val cardSearch = CardSearch(
            List.of<Card?>(
                cards!!.get("Empyrean Eagle"),  // white blue
                cards.get("Angel of the Dawn"),  // white
                cards.get("Jousting Dummy") // colorless
            )
        )

        // When
        val result = cardSearch.colorless().getCards()

        // Then
        Assertions.assertThat<Card?>(result).contains(cards.get("Jousting Dummy"))
    }

    @Test
    fun multicolor() {
        // Given
        val cardSearch = CardSearch(
            List.of<Card?>(
                cards!!.get("Empyrean Eagle"),  // white blue
                cards.get("Angel of the Dawn"),  // white
                cards.get("Jousting Dummy") // colorless
            )
        )

        // When
        val result = cardSearch.multicolor().getCards()

        // Then
        Assertions.assertThat<Card?>(result).contains(cards.get("Empyrean Eagle"))
    }
}