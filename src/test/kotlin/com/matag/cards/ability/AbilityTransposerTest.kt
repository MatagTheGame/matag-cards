package com.matag.cards.ability

import com.matag.cards.ability.selector.SelectorType
import com.matag.cards.ability.trigger.TriggerSubtype
import com.matag.cards.ability.trigger.TriggerType
import com.matag.cards.ability.type.AbilityType
import com.matag.cards.properties.Type
import com.matag.player.PlayerType
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class AbilityTransposerTest {
    @Test
    fun notTransposableAbility() {
        // Given
        val lifelink = Ability(abilityType = AbilityType.LIFELINK)

        // When
        val transposed = AbilityTransposer.transpose(lifelink)

        // Then
        assertThat(transposed).isEqualTo(lifelink)
    }

    @Test
    fun transposeProwess() {
        // Given
        val prowess = Ability(abilityType = AbilityType.PROWESS)

        // When
        val transposed = AbilityTransposer.transpose(prowess)

        // Then
        assertThat<AbilityType?>(transposed.abilityType).isEqualTo(AbilityType.SELECTED_PERMANENTS_GET)
        assertThat(transposed.magicInstanceSelector!!.itself).isTrue()
        assertThat<String?>(transposed.parameters).containsExactly("+1/+1")
        assertThat<TriggerType?>(transposed.trigger!!.type).isEqualTo(TriggerType.TRIGGERED_ABILITY)
        assertThat(transposed.trigger!!.subtype!!).isEqualTo(TriggerSubtype.WHEN_CAST)
        assertThat<SelectorType?>(transposed.trigger!!.magicInstanceSelector!!.selectorType)
            .isEqualTo(SelectorType.SPELL)
        assertThat<Type?>(transposed.trigger!!.magicInstanceSelector!!.notOfType).containsExactly(
            Type.CREATURE
        )
        assertThat<PlayerType?>(transposed.trigger!!.magicInstanceSelector!!.controllerType)
            .isEqualTo(PlayerType.PLAYER)
    }
}