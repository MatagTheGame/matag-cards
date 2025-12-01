package com.matag.cards.ability

import com.matag.cards.ability.selector.MagicInstanceSelector
import com.matag.cards.ability.selector.SelectorType
import com.matag.cards.ability.trigger.Trigger
import com.matag.cards.ability.trigger.TriggerSubtype
import com.matag.cards.ability.trigger.TriggerType
import com.matag.cards.ability.type.AbilityType
import com.matag.cards.ability.type.AbilityType.LIFELINK
import com.matag.cards.ability.type.AbilityType.PROWESS
import com.matag.cards.properties.Type
import com.matag.player.PlayerType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class AbilityTransposerTest {
    @Test
    fun notTransposableAbility() {
        // When
        val transposed = Ability(LIFELINK).transpose()

        // Then
        assertThat(transposed).isEqualTo(Ability(LIFELINK))
    }

    @Test
    fun transposeProwess() {
        // When
        val transposed = Ability(PROWESS).transpose()

        // Then
        assertThat(transposed).isEqualTo(
            Ability(
                abilityType = AbilityType.SELECTED_PERMANENTS_GET,
                targets = null,
                magicInstanceSelector = MagicInstanceSelector(
                    selectorType = SelectorType.PERMANENT,
                    itself = true
                ),
                parameters = listOf("+1/+1"),
                trigger = Trigger(
                    type = TriggerType.TRIGGERED_ABILITY,
                    subtype = TriggerSubtype.WHEN_CAST,
                    magicInstanceSelector = MagicInstanceSelector(
                        selectorType = SelectorType.SPELL,
                        notOfType = listOf(Type.CREATURE),
                        controllerType = PlayerType.PLAYER
                    )
                ),
                ability = null,
                sorcerySpeed = false,
                optional = false
            )
        )
    }
}