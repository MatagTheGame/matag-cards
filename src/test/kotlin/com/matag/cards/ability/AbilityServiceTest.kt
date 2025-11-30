package com.matag.cards.ability

import com.matag.cards.ability.type.AbilityType
import com.matag.cards.properties.PowerToughness
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AbilityServiceTest {

    @Nested
    inner class PowerToughnessFromParameter {
        @Test
        fun `get value`() {
            // Given
            val parameter = "+2/+2"

            // When
            val powerToughness = AbilityService().powerToughnessFromParameter(parameter)

            // Then
            assertThat(powerToughness).isEqualTo(PowerToughness(2, 2))
        }

        @Test
        fun `absent value`() {
            // Given
            val parameter = "TRAMPLE"

            // When
            val powerToughness = AbilityService().powerToughnessFromParameter(parameter)

            // Then
            assertThat(powerToughness).isEqualTo(PowerToughness(0, 0))
        }

        @Test
        fun `get value from list`() {
            // Given
            val parameters = listOf("TRAMPLE", "+2/+2", "HASTE")

            // When
            val powerToughness = AbilityService().powerToughnessFromParameters(parameters)

            // Then
            assertThat(powerToughness).isEqualTo(PowerToughness(2, 2))
        }
    }

    @Nested
    inner class DamageFromParameter {
        @Test
        fun `get value`() {
            // Given
            val parameter = "DAMAGE:4"

            // When
            val damage = AbilityService().damageFromParameter(parameter)

            // Then
            assertThat(damage).isEqualTo(4)
        }

        @Test
        fun `absent value`() {
            // When
            val damage = AbilityService().damageFromParameter("TRAMPLE")

            // Then
            assertThat(damage).isEqualTo(0)
        }
    }

    @Test
    fun testControllerDamageFromParameter() {
        // Given
        val parameter = "CONTROLLER_DAMAGE:5"

        // When
        val damage = AbilityService().controllerDamageFromParameter(parameter)

        // Then
        assertThat(damage).isEqualTo(5)
    }

    @Test
    fun testTappedFromParameter() {
        // Given
        val parameter = ":TAPPED"

        // When
        val tapped = AbilityService().tappedFromParameter(parameter)

        // Then
        assertThat(tapped).isTrue()
    }

    @Test
    fun testUntappedFromParameter() {
        // Given
        val parameter = ":UNTAPPED"

        // When
        val untapped = AbilityService().untappedFromParameter(parameter)

        // Then
        assertThat(untapped).isTrue()
    }

    @Test
    fun testDoesNotUntapNextTurnFromParameter() {
        // Given
        val parameter = ":DOES_NOT_UNTAP_NEXT_TURN"

        // When
        val doesNotUntapNextTurn = AbilityService().doesNotUntapNextTurnFromParameter(parameter)

        // Then
        assertThat(doesNotUntapNextTurn).isTrue()
    }

    @Test
    fun testDestroyedFromParameter() {
        // Given
        val parameter = ":DESTROYED"

        // When
        val destroyed = AbilityService().destroyedFromParameter(parameter)

        // Then
        assertThat(destroyed).isTrue()
    }

    @Test
    fun testReturnedToOwnerHandFromParameter() {
        // Given
        val parameter = ":RETURN_TO_OWNER_HAND"

        // When
        val returnToOwnerHand = AbilityService().returnToOwnerHandFromParameter(parameter)

        // Then
        assertThat(returnToOwnerHand).isTrue()
    }

    @Test
    fun testControlledFromParameter() {
        // Given
        val parameter = ":CONTROLLED"

        // When
        val controlled = AbilityService().controlledFromParameter(parameter)

        // Then
        assertThat(controlled).isTrue()
    }

    @Test
    fun testPlus1CountersFromParameter() {
        // Given
        val parameter = "PLUS_1_COUNTERS:2"

        // When
        val counters = AbilityService().plus1CountersFromParameter(parameter)

        // Then
        assertThat(counters).isEqualTo(2)
    }

    @Test
    fun testMinus1CountersFromParameter() {
        // Given
        val parameter = "MINUS_1_COUNTERS:3"

        // When
        val counters = AbilityService().minus1CountersFromParameter(parameter)

        // Then
        assertThat(counters).isEqualTo(3)
    }

    @Test
    fun testKeywordCounterFromParameter() {
        // Given
        val parameter = "KEYWORD_COUNTER:MENACE"

        // When
        val keywordAbility = AbilityService().keywordCounterFromParameter(parameter)

        // Then
        assertThat<AbilityType?>(keywordAbility).isEqualTo(AbilityType.MENACE)
    }

    @Nested
    inner class DrawFromParameter {
        @Test
        fun testDrawFromParameter() {
            // Given
            val parameter = "DRAW:2"

            // When
            val draw = AbilityService().drawFromParameter(parameter)

            // Then
            assertThat(draw).isEqualTo(2)
        }

        @Test
        fun testDrawFromParameterAbsent() {
            // When
            val draw = AbilityService().drawFromParameter("TRAMPLE")

            // Then
            assertThat(draw).isEqualTo(0)
        }
    }

    @Test
    fun testLifeFromParameter() {
        // Given
        val parameter = "LIFE:3"

        // When
        val life = AbilityService().lifeFromParameter(parameter)

        // Then
        assertThat(life).isEqualTo(3)
    }

    @Nested
    inner class ParametersAsString {
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
            val parametersAsString = AbilityService().parametersAsString(parameters)

            // Then
            assertThat(parametersAsString)
                .isEqualTo("+2/+2, trample, 2 damage, haste, to its controller 3 damage, tapped, doesn't untap next turn, destroyed, returned to its owner's hand, 2 +1/+1 counters and a menace counter")
        }

        @Test
        fun testPlayerParametersAsString() {
            // Given
            val parameters = mutableListOf<String?>("LIFE:2", "LIFE:-3", "DRAW:1", "DRAW:2")

            // When
            val parametersAsString = AbilityService().parametersAsString(parameters)

            // Then
            assertThat(parametersAsString).isEqualTo("gain 2 life, lose 3 life, draw 1 card and draw 2 cards")
        }
    }
}