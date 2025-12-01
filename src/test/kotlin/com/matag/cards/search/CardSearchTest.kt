package com.matag.cards.search

import com.matag.cards.Cards
import com.matag.cards.CardsConfiguration
import com.matag.cards.properties.Color.BLUE
import com.matag.cards.properties.Color.WHITE
import com.matag.cards.properties.Subtype
import com.matag.cards.properties.Type
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@Import(CardsConfiguration::class)
class CardSearchTest(
    @param:Autowired val cards: Cards
) {

    @Test
    fun empty() {
        assertThat(CardSearch(listOf()).isEmpty()).isTrue()
    }

    @Test
    fun notEmpty() {
        assertThat(CardSearch(listOf(cards.get("Plains"))).isNotEmpty()).isTrue()
    }

    @Test
    fun concat() {
        // When
        val result = CardSearch(listOf(cards.get("Plains")))
            .concat(listOf(cards.get("Mountain")))
            .cards

        // Then
        assertThat(result).hasSize(2)
    }

    @Test
    fun ofType() {
        // Given
        val cardSearch = CardSearch(listOf(cards.get("Plains"), cards.get("Bedevil")))

        // When
        val result = cardSearch.ofType(Type.LAND).cards

        // Then
        assertThat(result).contains(cards.get("Plains"))
    }

    @Test
    fun notOfType() {
        // Given
        val cardSearch = CardSearch(listOf(cards.get("Plains"), cards.get("Bedevil")))

        // When
        val result = cardSearch.notOfType(Type.LAND).cards

        // Then
        assertThat(result).contains(cards.get("Bedevil"))
    }

    @Test
    fun ofSubtype() {
        // Given
        val cardSearch = CardSearch(listOf(cards.get("Plains"), cards.get("Dusk Legion Zealot")))

        // When
        val result = cardSearch.ofSubtype(Subtype.SOLDIER).cards

        // Then
        assertThat(result).contains(cards.get("Dusk Legion Zealot"))
    }

    @Test
    fun notOfSubtype() {
        // Given
        val cardSearch = CardSearch(listOf(cards.get("Plains"), cards.get("Dusk Legion Zealot")))

        // When
        val result = cardSearch.notOfSubtype(Subtype.SOLDIER).cards

        // Then
        assertThat(result).contains(cards.get("Plains"))
    }

    @Test
    fun ofColor() {
        // Given
        val cardSearch = CardSearch(
            listOf(
                cards.get("Empyrean Eagle"), // white blue
                cards.get("Angel of the Dawn"), // white
                cards.get("Dark Nourishment") // black
            )
        )

        // When
        val result = cardSearch.ofColor(WHITE).cards

        // Then
        assertThat(result).contains(cards.get("Empyrean Eagle"), cards.get("Angel of the Dawn"))
    }

    @Test
    fun ofOnlyAnyOfTheColors() {
        // Given
        val cardSearch = CardSearch(
            listOf(
                cards.get("Empyrean Eagle"), // white blue
                cards.get("Angel of the Dawn"), // white
                cards.get("Swiftblade Vindicator"), // white red
                cards.get("Dark Nourishment") // black
            )
        )

        // When
        val result = cardSearch.ofOnlyAnyOfTheColors(setOf(WHITE, BLUE)).cards

        // Then
        assertThat(result).contains(cards.get("Empyrean Eagle"), cards.get("Angel of the Dawn"))
    }

    @Test
    fun colorless() {
        // Given
        val cardSearch = CardSearch(
            listOf(
                cards.get("Empyrean Eagle"), // white blue
                cards.get("Angel of the Dawn"), // white
                cards.get("Jousting Dummy") // colorless
            )
        )

        // When
        val result = cardSearch.colorless().cards

        // Then
        assertThat(result).contains(cards.get("Jousting Dummy"))
    }

    @Test
    fun multicolor() {
        // Given
        val cardSearch = CardSearch(
            listOf(
                cards.get("Empyrean Eagle"), // white blue
                cards.get("Angel of the Dawn"), // white
                cards.get("Jousting Dummy") // colorless
            )
        )

        // When
        val result = cardSearch.multicolor().cards

        // Then
        assertThat(result).contains(cards.get("Empyrean Eagle"))
    }
}