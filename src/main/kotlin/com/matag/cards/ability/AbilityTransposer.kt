package com.matag.cards.ability

import com.matag.cards.ability.selector.MagicInstanceSelector
import com.matag.cards.ability.selector.SelectorType
import com.matag.cards.ability.trigger.Trigger
import com.matag.cards.ability.trigger.TriggerSubtype
import com.matag.cards.ability.trigger.TriggerType
import com.matag.cards.ability.type.AbilityType
import com.matag.cards.properties.Type
import com.matag.player.PlayerType

private val THIS_PERMANENT = MagicInstanceSelector(
    selectorType = SelectorType.PERMANENT,
    itself = true
)

private val YOUR_NON_CREATURES_SPELL = MagicInstanceSelector(
    selectorType = SelectorType.SPELL,
    notOfType = listOf(Type.CREATURE),
    controllerType = PlayerType.PLAYER
)

/**
 * If a complex ability can be transposed in a simpler ability already implemented then go for it.
 */
fun Ability.transpose(): Ability {
    if (this.abilityType == AbilityType.PROWESS) {
        return Ability(
            abilityType = AbilityType.SELECTED_PERMANENTS_GET,
            targets = null,
            magicInstanceSelector = THIS_PERMANENT,
            parameters = listOf("+1/+1"),
            trigger = Trigger(
                type = TriggerType.TRIGGERED_ABILITY,
                subtype = TriggerSubtype.WHEN_CAST,
                magicInstanceSelector = YOUR_NON_CREATURES_SPELL
            ),
            ability = null,
            sorcerySpeed = false,
            optional = false
        )
    }

    return this
}
