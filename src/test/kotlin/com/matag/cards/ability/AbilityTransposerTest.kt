package com.matag.cards.ability

import com.matag.cards.ability.selector.SelectorType
import com.matag.cards.ability.trigger.TriggerSubtype
import com.matag.cards.ability.trigger.TriggerType
import com.matag.cards.ability.type.AbilityType
import com.matag.cards.properties.Type
import com.matag.player.PlayerType
import org.assertj.core.api.Assertions
import org.junit.Test

class AbilityTransposerTest {
    @Test
    fun notTransposableAbility() {
        // Given
        val lifelink = Ability.builder().abilityType(AbilityType.LIFELINK).build()

        // When
        val transposed = AbilityTransposer.transpose(lifelink)

        // Then
        Assertions.assertThat<Ability?>(transposed).isEqualTo(lifelink)
    }

    @Test
    fun transposeProwess() {
        // Given
        val prowess = Ability.builder().abilityType(AbilityType.PROWESS).build()

        // When
        val transposed = AbilityTransposer.transpose(prowess)

        // Then
        Assertions.assertThat<AbilityType?>(transposed.getAbilityType()).isEqualTo(AbilityType.SELECTED_PERMANENTS_GET)
        Assertions.assertThat<SelectorType?>(transposed.getMagicInstanceSelector().getSelectorType())
            .isEqualTo(SelectorType.PERMANENT)
        Assertions.assertThat(transposed.getMagicInstanceSelector().isItself()).isTrue()
        Assertions.assertThat<String?>(transposed.getParameters()).containsExactly("+1/+1")
        Assertions.assertThat<TriggerType?>(transposed.getTrigger().getType()).isEqualTo(TriggerType.TRIGGERED_ABILITY)
        Assertions.assertThat<TriggerSubtype?>(transposed.getTrigger().getSubtype()).isEqualTo(TriggerSubtype.WHEN_CAST)
        Assertions.assertThat<SelectorType?>(transposed.getTrigger().getMagicInstanceSelector().getSelectorType())
            .isEqualTo(SelectorType.SPELL)
        Assertions.assertThat<Type?>(transposed.getTrigger().getMagicInstanceSelector().getNotOfType()).containsExactly(
            Type.CREATURE
        )
        Assertions.assertThat<PlayerType?>(transposed.getTrigger().getMagicInstanceSelector().getControllerType())
            .isEqualTo(PlayerType.PLAYER)
    }
}