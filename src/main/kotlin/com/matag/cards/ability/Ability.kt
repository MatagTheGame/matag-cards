package com.matag.cards.ability

import com.matag.cards.ability.selector.MagicInstanceSelector
import com.matag.cards.ability.target.Target
import com.matag.cards.ability.trigger.Trigger
import com.matag.cards.ability.type.AbilityType

data class Ability(
    val abilityType: AbilityType,
    val targets: List<Target>? = null,
    val magicInstanceSelector: MagicInstanceSelector? = null,
    val parameters: List<String>? = null,
    val trigger: Trigger? = null,
    val ability: Ability? = null,
    val sorcerySpeed: Boolean = false,
    val optional: Boolean = false
) {
    constructor(abilityType: AbilityType) : this(abilityType = abilityType, targets = null)
}