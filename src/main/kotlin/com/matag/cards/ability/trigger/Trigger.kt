package com.matag.cards.ability.trigger

import com.matag.cards.ability.selector.MagicInstanceSelector
import com.matag.cards.properties.Cost

data class Trigger(
    var type: TriggerType? = null,
    var subtype: TriggerSubtype? = null,
    var cost: MutableList<Cost?>? = null,
    var magicInstanceSelector: MagicInstanceSelector? = null
) {
    companion object {
        fun castTrigger(): Trigger {
            return Trigger(TriggerType.CAST, null, null, null)
        }

        fun manaAbilityTrigger(): Trigger {
            return Trigger(TriggerType.MANA_ABILITY, null, null, null)
        }

        fun triggeredAbility(triggerSubtype: TriggerSubtype, magicInstanceSelector: MagicInstanceSelector): Trigger {
            return Trigger(TriggerType.TRIGGERED_ABILITY, triggerSubtype, null, magicInstanceSelector)
        }

        fun activatedAbility(cost: MutableList<Cost?>): Trigger {
            return Trigger(TriggerType.ACTIVATED_ABILITY, null, cost, null)
        }

        fun staticAbility(): Trigger {
            return Trigger(TriggerType.STATIC, null, null, null)
        }
    }
}
