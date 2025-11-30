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
    private val THIS_PERMANENT = MagicInstanceSelector(
        selectorType = SelectorType.PERMANENT,
        itself = true
    )

    private val YOUR_NON_CREATURES_SPELL = MagicInstanceSelector(
        selectorType = SelectorType.SPELL,
        notOfType = mutableListOf(Type.CREATURE),
        controllerType = PlayerType.PLAYER
    )

    fun transpose(ability: Ability): Ability {
        if (ability.abilityType == AbilityType.PROWESS) {
            return Ability(
                AbilityType.SELECTED_PERMANENTS_GET,
                null,
                THIS_PERMANENT,
                mutableListOf("+1/+1"),
                Trigger(
                    type = TriggerType.TRIGGERED_ABILITY,
                    subtype = TriggerSubtype.WHEN_CAST,
                    magicInstanceSelector = YOUR_NON_CREATURES_SPELL
                ),
                null,
                false,
                false
            )
        }
        return ability
    }
}
