package com.matag.cards

import com.matag.cards.ability.Ability
import com.matag.cards.ability.AbilityService
import com.matag.cards.ability.selector.MagicInstanceSelector
import com.matag.cards.ability.selector.SelectorType
import com.matag.cards.ability.target.Target
import com.matag.cards.ability.trigger.Trigger
import com.matag.cards.ability.type.AbilityType
import com.matag.cards.properties.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.function.Consumer

@ExtendWith(SpringExtension::class)
@Import(CardsConfiguration::class)
class CardsTest {
    @Autowired
    private val cards: Cards? = null

    @Autowired
    private val abilityService: AbilityService? = null

    @Test
    fun shouldLoadAllCards() {
        Assertions.assertThat<Card?>(cards!!.all).isNotEmpty()

        for (card in cards.all) {
            validateCard(card!!)
        }
    }

    @Test
    fun shouldLoadACardWithoutAbilities() {
        val card = cards!!.get("Feral Maaka")
        Assertions.assertThat(card!!.name).isEqualTo("Feral Maaka")
        Assertions.assertThat<Color>(card.colors).containsExactly(Color.RED)
        Assertions.assertThat<Cost>(card.cost).containsExactly(Cost.RED, Cost.ANY)
        Assertions.assertThat<Type>(card.types).containsExactly(Type.CREATURE)
        Assertions.assertThat<Subtype>(card.subtypes).containsExactlyInAnyOrder(Subtype.CAT)
        Assertions.assertThat<Rarity?>(card.rarity).isEqualTo(Rarity.COMMON)
        Assertions.assertThat(card.ruleText).isNullOrEmpty()
        Assertions.assertThat(card.power).isEqualTo(2)
        Assertions.assertThat(card.toughness).isEqualTo(2)
    }

    @Test
    fun shouldLoadACardWithAbilities() {
        val card = cards!!.get("Act of Treason")
        Assertions.assertThat(card!!.name).isEqualTo("Act of Treason")
        Assertions.assertThat<Color>(card.colors).containsExactly(Color.RED)
        Assertions.assertThat<Cost>(card.cost).containsExactly(Cost.RED, Cost.ANY, Cost.ANY)
        Assertions.assertThat<Type>(card.types).containsExactly(Type.SORCERY)
        Assertions.assertThat<Subtype>(card.subtypes).isNullOrEmpty()
        Assertions.assertThat<Rarity?>(card.rarity).isEqualTo(Rarity.COMMON)
        Assertions.assertThat(card.ruleText)
            .isEqualTo("Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.")
        Assertions.assertThat(card.power).isNull()
        Assertions.assertThat(card.toughness).isNull()
        Assertions.assertThat<Ability>(card.abilities).hasSize(1)
        Assertions.assertThat<Ability>(card.abilities!!.get(0)).isEqualTo(
            Ability(
                abilityType = AbilityType.THAT_TARGETS_GET,
                targets = mutableListOf<Target?>(Target(magicInstanceSelector = MagicInstanceSelector(selectorType = SelectorType.PERMANENT, ofType = listOf<Type>(Type.CREATURE)))),
                parameters = mutableListOf<String?>(":CONTROLLED", ":UNTAPPED", "HASTE"),
                trigger = Trigger.castTrigger()
            )
        )
    }

    private fun validateCard(card: Card) {
        val name = card.name
        if (card.imageUrl == null) {
            throw RuntimeException("Card '" + name + "' does not have an imageUrl. Run cardImageLinker")
        }

        if (card.types!!.isEmpty()) {
            throw RuntimeException("Card '" + name + "' does not have a type. Remove the image and run cardImageLinker")
        }

        if (card.abilities != null) {
            card.abilities.stream()
                .filter { ability: Ability? -> ability!!.abilityType == AbilityType.THAT_TARGETS_GET }
                .forEach { ability: Ability? ->
                    if (ability!!.targets!!.isEmpty()) {
                        throw RuntimeException("Card '" + name + "' is missing targets")
                    }
                    validateParameters(name, ability.parameters!!)
                }

            card.abilities.stream()
                .filter { ability: Ability? -> ability!!.abilityType == AbilityType.SELECTED_PERMANENTS_GET }
                .forEach { ability: Ability? ->
                    if (ability!!.magicInstanceSelector == null) {
                        throw RuntimeException("Card '" + name + "' is missing magicInstanceSelector")
                    }
                    validateParameters(name, ability.parameters!!)
                }
        }
    }

    private fun validateParameters(name: String?, parameters: MutableList<String?>) {
        if (parameters.isEmpty()) {
            throw RuntimeException("Card '" + name + "' is missing parameters")
        }

        try {
            parameters.forEach(Consumer { parameter: String? -> abilityService!!.parameterAsString(parameter!!) })
        } catch (e: Exception) {
            throw RuntimeException("Card '" + name + "' has invalid parameters: " + parameters, e)
        }
    }
}
