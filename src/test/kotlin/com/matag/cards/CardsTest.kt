package com.matag.cards

import com.matag.cards.ability.Ability
import com.matag.cards.ability.AbilityService
import com.matag.cards.ability.selector.MagicInstanceSelector
import com.matag.cards.ability.selector.SelectorType
import com.matag.cards.ability.target.Target
import com.matag.cards.ability.trigger.Trigger
import com.matag.cards.ability.type.AbilityType
import com.matag.cards.properties.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@Import(CardsConfiguration::class)
class CardsTest(
    @param:Autowired val cards: Cards,
    @param:Autowired val abilityService: AbilityService
) {
    @Test
    fun shouldLoadAllCards() {
        assertThat(cards.all()).isNotEmpty()
        
        cards.all().forEach {
            requireNotNull(it.imageUrl) { "Card '${it.name}' does not have an imageUrl. Run cardImageLinker" }
            require(it.types?.isNotEmpty() == true) { "Card '${it.name}' does not have a type. Remove the image and run cardImageLinker" }

            it.abilities
                ?.filter { it.abilityType == AbilityType.THAT_TARGETS_GET }
                ?.forEach { ability ->
                    if (ability.targets!!.isEmpty()) {
                        throw Exception("Card '" + it.name + "' is missing targets")
                    }
                    validateParameters(it.name, ability.parameters!!)
                }

            it.abilities
                ?.filter { it.abilityType == AbilityType.SELECTED_PERMANENTS_GET }
                ?.forEach { ability ->
                    if (ability.magicInstanceSelector == null) {
                        throw Exception("Card '" + it.name + "' is missing magicInstanceSelector")
                    }
                    validateParameters(it.name, ability.parameters!!)
                }
        }
    }

    @Test
    fun shouldLoadACardWithoutAbilities() {
        val card = cards.get("Feral Maaka")
        assertThat(card.name).isEqualTo("Feral Maaka")
        assertThat(card.colors).containsExactly(Color.RED)
        assertThat(card.cost).containsExactly(Cost.RED, Cost.ANY)
        assertThat(card.types).containsExactly(Type.CREATURE)
        assertThat(card.subtypes).containsExactlyInAnyOrder(Subtype.CAT)
        assertThat(card.rarity).isEqualTo(Rarity.COMMON)
        assertThat(card.ruleText).isNullOrEmpty()
        assertThat(card.power).isEqualTo(2)
        assertThat(card.toughness).isEqualTo(2)
    }

    @Test
    fun shouldLoadACardWithAbilities() {
        val card = cards.get("Act of Treason")
        assertThat(card.name).isEqualTo("Act of Treason")
        assertThat(card.colors).containsExactly(Color.RED)
        assertThat(card.cost).containsExactly(Cost.RED, Cost.ANY, Cost.ANY)
        assertThat(card.types).containsExactly(Type.SORCERY)
        assertThat(card.subtypes).isNullOrEmpty()
        assertThat(card.rarity).isEqualTo(Rarity.COMMON)
        assertThat(card.ruleText)
            .isEqualTo("Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.")
        assertThat(card.power).isNull()
        assertThat(card.toughness).isNull()
        assertThat(card.abilities).hasSize(1)
        assertThat(card.abilities!!.get(0)).isEqualTo(
            Ability(
                abilityType = AbilityType.THAT_TARGETS_GET,
                targets = listOf(Target(magicInstanceSelector = MagicInstanceSelector(selectorType = SelectorType.PERMANENT, ofType = listOf(Type.CREATURE)))),
                parameters = listOf(":CONTROLLED", ":UNTAPPED", "HASTE"),
                trigger = Trigger.castTrigger()
            )
        )
    }

    private fun validateParameters(name: String?, parameters: List<String>) {
        if (parameters.isEmpty()) {
            throw Exception("Card '$name' is missing parameters")
        }

        try {
            abilityService.parametersAsString(parameters)
        } catch (e: Exception) {
            throw Exception("Card '$name' has invalid parameters: $parameters", e)
        }
    }
}
