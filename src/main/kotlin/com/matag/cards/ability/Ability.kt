package com.matag.cards.ability

import com.fasterxml.jackson.annotation.JsonProperty
import com.matag.cards.Card
import com.matag.cards.ability.selector.MagicInstanceSelector
import com.matag.cards.ability.selector.SelectorType
import com.matag.cards.ability.target.Target
import com.matag.cards.ability.trigger.Trigger
import com.matag.cards.ability.type.AbilityType
import com.matag.cards.ability.type.AbilityType.SELECTED_PERMANENTS_GET
import kotlin.collections.map
import kotlin.collections.orEmpty

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
    // This is only needed because I'm using java in matag-the-game module
    constructor(abilityType: AbilityType) : this(abilityType = abilityType, targets = null)

    constructor(ability: Ability) : this(
        ability.abilityType,
        ability.targets,
        ability.magicInstanceSelector,
        ability.parameters,
        ability.trigger,
        ability.ability,
        ability.sorcerySpeed,
        ability.optional
    )

    @get:JsonProperty
    val abilityTypeText: String
        get() {
            val parametersString = parameters?.let {
                AbilityService().parametersAsString(it)
            }

            if (abilityType == SELECTED_PERMANENTS_GET) {
                requireNotNull(magicInstanceSelector) { "$SELECTED_PERMANENTS_GET requires a magicInstanceSelector" }
                return if (magicInstanceSelector.selectorType == SelectorType.PLAYER) {
                    String.format(abilityType.text, magicInstanceSelector.text(), parametersString) + "."
                } else {
                    String.format(abilityType.text, magicInstanceSelector.text(), parametersString) + " until end of turn."
                }
            } else {
                return String.format(abilityType.text, parametersString)
            }
        }

    companion object {
        @JvmStatic
        fun abilities(card: Card): List<Ability> =
            card.abilities.orEmpty()
                .map { it.transpose() }
    }
}