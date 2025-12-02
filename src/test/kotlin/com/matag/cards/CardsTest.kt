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
                    require(ability.targets?.isNotEmpty() == true) { "Card '${it.name}' is missing targets" }
                    validateParameters(it.name, ability.parameters)
                }

            it.abilities
                ?.filter { it.abilityType == AbilityType.SELECTED_PERMANENTS_GET }
                ?.forEach { ability ->
                    requireNotNull(ability.magicInstanceSelector) { "Card '${it.name}' is missing magicInstanceSelector" }
                    validateParameters(it.name, ability.parameters)
                }
        }
    }

    @Test
    fun shouldLoadACardWithoutAbilities() {
        val card = cards.get("Feral Maaka")

        assertThat(card).isEqualTo(Card(
            name = "Feral Maaka",
            imageUrl = "https://cards.scryfall.io/large/front/3/c/3c969aa0-b0e5-42cd-abba-0a3c7266142c.jpg",
            colors = sortedSetOf(Color.RED),
            cost = listOf(Cost.RED, Cost.ANY),
            types = sortedSetOf(Type.CREATURE),
            subtypes = sortedSetOf(Subtype.CAT),
            rarity = Rarity.COMMON,
            power = 2,
            toughness = 2
        ))
    }

    @Test
    fun shouldLoadACardWithAbilities() {
        val card = cards.get("Act of Treason")

        assertThat(card).isEqualTo(Card(
            name = "Act of Treason",
            imageUrl = "https://cards.scryfall.io/large/front/7/8/78fec7e6-5ed9-46dc-93b4-7a054d763403.jpg",
            colors = sortedSetOf(Color.RED),
            cost = listOf(Cost.RED, Cost.ANY, Cost.ANY),
            types = sortedSetOf(Type.SORCERY),
            rarity = Rarity.COMMON,
            ruleText = "Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.",
            abilities = listOf(
                Ability(
                    abilityType = AbilityType.THAT_TARGETS_GET,
                    targets = listOf(Target(magicInstanceSelector = MagicInstanceSelector(selectorType = SelectorType.PERMANENT, ofType = listOf(Type.CREATURE)))),
                    parameters = listOf(":CONTROLLED", ":UNTAPPED", "HASTE"),
                    trigger = Trigger.castTrigger()
                )
            )
        ))
    }

    private fun validateParameters(name: String, parameters: List<String>?) {
        require(parameters?.isNotEmpty() == true) { "Card '$name' is missing parameters" }

        try {
            abilityService.parametersAsString(parameters)
        } catch (e: Exception) {
            throw Exception("Card '$name' has invalid parameters: $parameters", e)
        }
    }
}
