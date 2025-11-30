package com.matag.cards.ability

import com.matag.cards.ability.selector.MagicInstanceSelector
import com.matag.cards.ability.selector.SelectorType
import com.matag.cards.ability.trigger.Trigger
import com.matag.cards.ability.trigger.TriggerSubtype
import com.matag.cards.ability.trigger.TriggerType
import com.matag.cards.ability.type.AbilityType
import com.matag.cards.properties.Type
import com.matag.player.PlayerType

object AbilityTransposer {
    private val SELECTOR_IT: MagicInstanceSelector? = MagicInstanceSelector(itself = true)
    private val YOUR_NON_CREATURE_SPELL: MagicInstanceSelector? =
        MagicInstanceSelector(
            selectorType = SelectorType.SPELL,
            notOfType = mutableListOf(Type.CREATURE),
            controllerType = PlayerType.PLAYER
        )

    fun transpose(ability: Ability): Ability {
        if (ability.abilityType == AbilityType.PROWESS) {
            return Ability(
                AbilityType.SELECTED_PERMANENTS_GET,
                null,
                SELECTOR_IT,
                mutableListOf("+1/+1"),
                Trigger(
                    type = TriggerType.TRIGGERED_ABILITY,
                    subtype = TriggerSubtype.WHEN_CAST,
                    magicInstanceSelector = YOUR_NON_CREATURE_SPELL
                ),
                null,
                false,
                false
            )
        }
        return ability
    }
}
