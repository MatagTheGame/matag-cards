package com.matag.cards.ability

import com.matag.cards.ability.type.AbilityType
import com.matag.cards.properties.PowerToughness
import org.assertj.core.api.Assertions
import org.junit.Test

class AbilityServiceTest {
    private val abilityService = AbilityService()

    @Test
    fun testPowerAndToughnessFromParameter() {
        // Given
        val parameter = "+2/+2"

        // When
        val PowerToughness = abilityService.powerToughnessFromParameter(parameter)

        // Then
        Assertions.assertThat<PowerToughness?>(PowerToughness).isEqualTo(PowerToughness(2, 2))
    }

    @Test
    fun testPowerAndToughnessFromParameterAbsent() {
        // Given
        val parameter = "TRAMPLE"

        // When
        val PowerToughness = abilityService.powerToughnessFromParameter(parameter)

        // Then
        Assertions.assertThat<PowerToughness?>(PowerToughness).isEqualTo(PowerToughness(0, 0))
    }

    @Test
    fun testPowerAndToughnessFromParameters() {
        // Given
        val parameters = mutableListOf<String?>("TRAMPLE", "+2/+2", "HASTE")

        // When
        val PowerToughness = abilityService.powerToughnessFromParameters(parameters)

        // Then
        Assertions.assertThat<PowerToughness?>(PowerToughness).isEqualTo(PowerToughness(2, 2))
    }

    @Test
    fun testPowerAndToughnessFromParametersAbsent() {
        // Given
        val parameter = "TRAMPLE"

        // When
        val PowerToughness = abilityService.powerToughnessFromParameter(parameter)

        // Then
        Assertions.assertThat<PowerToughness?>(PowerToughness).isEqualTo(PowerToughness(0, 0))
    }

    @Test
    fun testDamageFromParameter() {
        // Given
        val parameter = "DAMAGE:4"

        // When
        val damage = abilityService.damageFromParameter(parameter)

        // Then
        Assertions.assertThat(damage).isEqualTo(4)
    }

    @Test
    fun testDamageFromParameterAbsent() {
        // When
        val damage = abilityService.damageFromParameter("TRAMPLE")

        // Then
        Assertions.assertThat(damage).isEqualTo(0)
    }

    @Test
    fun testControllerDamageFromParameter() {
        // Given
        val parameter = "CONTROLLER_DAMAGE:5"

        // When
        val damage = abilityService.controllerDamageFromParameter(parameter)

        // Then
        Assertions.assertThat(damage).isEqualTo(5)
    }

    @Test
    fun testTappedFromParameter() {
        // Given
        val parameter = ":TAPPED"

        // When
        val tapped = abilityService.tappedFromParameter(parameter)

        // Then
        Assertions.assertThat(tapped).isTrue()
    }

    @Test
    fun testUntappedFromParameter() {
        // Given
        val parameter = ":UNTAPPED"

        // When
        val untapped = abilityService.untappedFromParameter(parameter)

        // Then
        Assertions.assertThat(untapped).isTrue()
    }

    @Test
    fun testDoesNotUntapNextTurnFromParameter() {
        // Given
        val parameter = ":DOES_NOT_UNTAP_NEXT_TURN"

        // When
        val doesNotUntapNextTurn = abilityService.doesNotUntapNextTurnFromParameter(parameter)

        // Then
        Assertions.assertThat(doesNotUntapNextTurn).isTrue()
    }

    @Test
    fun testDestroyedFromParameter() {
        // Given
        val parameter = ":DESTROYED"

        // When
        val destroyed = abilityService.destroyedFromParameter(parameter)

        // Then
        Assertions.assertThat(destroyed).isTrue()
    }

    @Test
    fun testReturnedToOwnerHandFromParameter() {
        // Given
        val parameter = ":RETURN_TO_OWNER_HAND"

        // When
        val returnToOwnerHand = abilityService.returnToOwnerHandFromParameter(parameter)

        // Then
        Assertions.assertThat(returnToOwnerHand).isTrue()
    }

    @Test
    fun testControlledFromParameter() {
        // Given
        val parameter = ":CONTROLLED"

        // When
        val controlled = abilityService.controlledFromParameter(parameter)

        // Then
        Assertions.assertThat(controlled).isTrue()
    }

    @Test
    fun testPlus1CountersFromParameter() {
        // Given
        val parameter = "PLUS_1_COUNTERS:2"

        // When
        val counters = abilityService.plus1CountersFromParameter(parameter)

        // Then
        Assertions.assertThat(counters).isEqualTo(2)
    }

    @Test
    fun testMinus1CountersFromParameter() {
        // Given
        val parameter = "MINUS_1_COUNTERS:3"

        // When
        val counters = abilityService.minus1CountersFromParameter(parameter)

        // Then
        Assertions.assertThat(counters).isEqualTo(3)
    }

    @Test
    fun testKeywordCounterFromParameter() {
        // Given
        val parameter = "KEYWORD_COUNTER:MENACE"

        // When
        val keywordAbility = abilityService.keywordCounterFromParameter(parameter)

        // Then
        Assertions.assertThat<AbilityType?>(keywordAbility).isEqualTo(AbilityType.MENACE)
    }


    @Test
    fun testDrawFromParameter() {
        // Given
        val parameter = "DRAW:2"

        // When
        val draw = abilityService.drawFromParameter(parameter)

        // Then
        Assertions.assertThat(draw).isEqualTo(2)
    }

    @Test
    fun testDrawFromParameterAbsent() {
        // When
        val draw = abilityService.drawFromParameter("TRAMPLE")

        // Then
        Assertions.assertThat(draw).isEqualTo(0)
    }

    @Test
    fun testLifeFromParameter() {
        // Given
        val parameter = "LIFE:3"

        // When
        val life = abilityService.lifeFromParameter(parameter)

        // Then
        Assertions.assertThat(life).isEqualTo(3)
    }

    @Test
    fun testPermanentParametersAsString() {
        // Given
        val parameters = mutableListOf<String?>(
            "+2/+2",
            "TRAMPLE",
            "DAMAGE:2",
            "HASTE",
            "CONTROLLER_DAMAGE:3",
            ":TAPPED",
            ":DOES_NOT_UNTAP_NEXT_TURN",
            ":DESTROYED",
            ":RETURN_TO_OWNER_HAND",
            "PLUS_1_COUNTERS:2",
            "KEYWORD_COUNTER:MENACE"
        )

        // When
        val parametersAsString = abilityService.parametersAsString(parameters)

        // Then
        Assertions.assertThat(parametersAsString)
            .isEqualTo("+2/+2, trample, 2 damage, haste, to its controller 3 damage, tapped, doesn't untap next turn, destroyed, returned to its owner's hand, 2 +1/+1 counters and a menace counter")
    }

    @Test
    fun testPlayerParametersAsString() {
        // Given
        val parameters = mutableListOf<String?>("LIFE:2", "LIFE:-3", "DRAW:1", "DRAW:2")

        // When
        val parametersAsString = abilityService.parametersAsString(parameters)

        // Then
        Assertions.assertThat(parametersAsString).isEqualTo("gain 2 life, lose 3 life, draw 1 card and draw 2 cards")
    }
}