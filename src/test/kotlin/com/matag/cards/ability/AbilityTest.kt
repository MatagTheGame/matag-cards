package com.matag.cards.ability

import com.matag.cards.Cards
import com.matag.cards.CardsConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@Import(CardsConfiguration::class)
class AbilityTest(
    @param:Autowired val cards: Cards,
) {
    @Test
    fun simpleAbilityText() {
        // Given
        val ability = cards.get("Aven Sentry").abilities?.first()

        // When
        val text = ability?.abilityTypeText

        // Then
        assertThat(text).isEqualTo("Flying.")
    }

    @Test
    fun enchantedCreatureGetOneAbilityText() {
        // Given
        val ability = cards.get("Knight's Pledge").abilities?.first()

        // When
        val text = ability?.abilityTypeText

        // Then
        assertThat(text).isEqualTo("Enchanted creature gets +2/+2.")
    }

    @Test
    fun enchantedCreatureGetMultipleAbilitiesText() {
        // Given
        val ability = cards.get("Arcane Flight").abilities?.first()

        // When
        val text = ability?.abilityTypeText

        // Then
        assertThat(text).isEqualTo("Enchanted creature gets +1/+1 and flying.")
    }

    @Test
    fun selectedPermanentsGetMultipleAbilitiesText() {
        // Given
        val ability = cards.get("Make a Stand").abilities?.first()

        // When
        val text = ability?.abilityTypeText

        // Then
        assertThat(text).isEqualTo("Creatures you control get +1/+0 and indestructible until end of turn.")
    }

    @Test
    fun itGetsAbilitiesText() {
        // Given
        val ability = cards.get("Brazen Wolves").abilities?.first()

        // When
        val text = ability?.abilityTypeText

        // Then
        assertThat(text).isEqualTo("Gets +2/+0 until end of turn.")
    }

    @Test
    fun gainXLifeText() {
        // Given
        val ability = cards.get("Highland Game").abilities?.first()

        // When
        val text = ability?.abilityTypeText

        // Then
        assertThat(text).isEqualTo("You gain 2 life.")
    }

    @Test
    fun drawXCardsAndLoseXLifeText() {
        // Given
        val ability = cards.get("Tithebearer Giant").abilities?.first()

        // When
        val text = ability?.abilityTypeText

        // Then
        assertThat(text).isEqualTo("You draw 1 card and lose 1 life.")
    }

    @Test
    fun eachPlayerGainsXLifeText() {
        // Given
        val ability = cards.get("Centaur Peacemaker").abilities?.first()

        // When
        val text = ability?.abilityTypeText

        // Then
        assertThat(text).isEqualTo("Each player gain 4 life.")
    }

    @Test
    fun eachOpponentLosesXLifeText() {
        // Given
        val ability = cards.get("Infectious Horror").abilities?.first()

        // When
        val text = ability?.abilityTypeText

        // Then
        assertThat(text).isEqualTo("Each opponent lose 2 life.")
    }
}