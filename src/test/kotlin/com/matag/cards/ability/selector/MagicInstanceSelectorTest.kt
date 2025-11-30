package com.matag.cards.ability.selector

import com.matag.cards.properties.Subtype
import com.matag.cards.properties.Type
import com.matag.player.PlayerType
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.List

class MagicInstanceSelectorTest {
    @Test
    fun itGetsText() {
        val magicInstanceSelector = MagicInstanceSelector(
            selectorType = SelectorType.PERMANENT,
            itself = true
        )
        assertThat(magicInstanceSelector.text()).isEqualTo("Gets")
    }

    @Test
    fun creaturesYouControlGetText() {
        val magicInstanceSelector = MagicInstanceSelector(
            selectorType = SelectorType.PERMANENT,
            ofType = listOf(Type.CREATURE),
            controllerType = PlayerType.PLAYER
        )
        assertThat(magicInstanceSelector.text()).isEqualTo("Creatures you control get")
    }

    @Test
    fun zombiesYouControlGetText() {
        val magicInstanceSelector = MagicInstanceSelector(
            selectorType = SelectorType.PERMANENT,
            ofType = listOf(Type.CREATURE),
            ofSubtype = listOf(Subtype.ZOMBIE),
            controllerType = PlayerType.PLAYER
        )
        assertThat(magicInstanceSelector.text()).isEqualTo("Zombies you control get")
    }

    @Test
    fun otherCreaturesYouControlGetText() {
        val magicInstanceSelector = MagicInstanceSelector(
            selectorType = SelectorType.PERMANENT,
            ofType = listOf(Type.CREATURE),
            controllerType = PlayerType.PLAYER,
            others = true
        )
        assertThat(magicInstanceSelector.text()).isEqualTo("Other creatures you control get")
    }

    @Test
    fun allOtherCreaturesGetText() {
        val magicInstanceSelector = MagicInstanceSelector(
            selectorType = SelectorType.PERMANENT,
            ofType = listOf(Type.CREATURE),
            others = true
        )
        assertThat(magicInstanceSelector.text()).isEqualTo("Other creatures get")
    }

    @Test
    fun enchantedAngels() {
        val magicInstanceSelector = MagicInstanceSelector(
            selectorType = SelectorType.PERMANENT,
            ofType = listOf(Type.CREATURE),
            ofSubtype = listOf(Subtype.ANGEL),
            currentEnchanted = true
        )
        assertThat(magicInstanceSelector.text()).isEqualTo("Enchanted angels get")
    }

    @Test
    fun playerYou() {
        val magicInstanceSelector = MagicInstanceSelector(
            selectorType = SelectorType.PLAYER,
            itself = true
        )
        assertThat(magicInstanceSelector.text()).isEqualTo("You")
    }

    @Test
    fun playerEachPlayer() {
        val magicInstanceSelector = MagicInstanceSelector(
            selectorType = SelectorType.PLAYER
        )
        assertThat(magicInstanceSelector.text()).isEqualTo("Each player")
    }

    @Test
    fun playerEachOpponent() {
        val magicInstanceSelector = MagicInstanceSelector(
            selectorType = SelectorType.PLAYER,
            controllerType = PlayerType.OPPONENT
        )
        assertThat(magicInstanceSelector.text()).isEqualTo("Each opponent")
    }
}