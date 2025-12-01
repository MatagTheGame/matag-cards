package com.matag.cards

import com.matag.cards.properties.Color.RED
import com.matag.cards.properties.Color.WHITE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@Import(CardsConfiguration::class)
class CardUtilsTest(
    @param:Autowired val cards: Cards
) {

    @ParameterizedTest
    @CsvSource(
        "Bishop's Soldier,false", // white
        "Jousting Dummy,true" // colorless
    )
    fun isColorless(cardName: String, result: Boolean) {
        assertThat(cards.get(cardName).isColorless()).isEqualTo(result)
    }

    @ParameterizedTest
    @CsvSource(
        "Inspiring Captain,true", // white
        "Fiery Finish,false", // red
        "Inspiring Veteran,true", // white-red
        "Jousting Dummy,false", // colorless
    )
    fun isOfColor(cardName: String, result: Boolean) {
        assertThat(cards.get(cardName).isOfColor(WHITE)).isEqualTo(result)
    }

    @ParameterizedTest
    @CsvSource(
        "Inspiring Captain,true", // white
        "Fiery Finish,true", // red
        "Inspiring Veteran,true", // white-red
        "Centaur Peacemaker,false", // white-green
        "Jousting Dummy,false", // colorless
    )
    fun isOnlyAnyOfTheColors(cardName: String, result: Boolean) {
        assertThat(cards.get(cardName).isOfOnlyAnyOfTheColors(setOf(WHITE, RED))).isEqualTo(result)
    }
}