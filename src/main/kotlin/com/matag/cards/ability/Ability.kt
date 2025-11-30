package com.matag.cards.ability

import com.matag.cards.ability.selector.MagicInstanceSelector
import com.matag.cards.ability.target.Target
import com.matag.cards.ability.trigger.Trigger
import com.matag.cards.ability.type.AbilityType

data class Ability(
    var abilityType: AbilityType? = null,
    var targets: List<Target>? = null,
    var magicInstanceSelector: MagicInstanceSelector? = null,
    var parameters: List<String>? = null,
    var trigger: Trigger? = null,
    var ability: Ability? = null,
    var sorcerySpeed: Boolean? = null,
    var optional: Boolean? = null
)