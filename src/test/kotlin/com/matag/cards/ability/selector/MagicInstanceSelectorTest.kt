package com.matag.cards.ability.selector

import com.matag.cards.properties.Subtype
import com.matag.cards.properties.Type
import com.matag.player.PlayerType
import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.List

class MagicInstanceSelectorTest {
    @Test
    fun creaturesYouControlGetText() {
        Assertions.assertThat(
            MagicInstanceSelector(
                selectorType = SelectorType.PERMANENT,
                ofType = mutableListOf(Type.CREATURE),
                controllerType = PlayerType.PLAYER
            ).text
        ).isEqualTo("Creatures you control get")
    }

    @Test
    fun zombiesYouControlGetText() {
        Assertions.assertThat(
            MagicInstanceSelector(
                selectorType = SelectorType.PERMANENT,
                ofType = mutableListOf<Type?>(Type.CREATURE),
                ofSubtype = List.of<Subtype?>(Subtype.ZOMBIE),
                controllerType = PlayerType.PLAYER
            ).text
        ).isEqualTo("Zombies you control get")
    }

    @Test
    fun otherCreaturesYouControlGetText() {
        Assertions.assertThat(
            MagicInstanceSelector(
                selectorType = SelectorType.PERMANENT,
                ofType = mutableListOf(Type.CREATURE),
                controllerType = PlayerType.PLAYER,
                others = true
            ).text
        ).isEqualTo("Other creatures you control get")
    }

    @Test
    fun allOtherCreaturesGetText() {
        Assertions.assertThat(
            MagicInstanceSelector(
                selectorType = SelectorType.PERMANENT,
                ofType = mutableListOf(Type.CREATURE),
                others = true
            ).text
        ).isEqualTo("Other creatures get")
    }

    @Test
    fun itGetsText() {
        Assertions.assertThat(
            MagicInstanceSelector(
                selectorType = SelectorType.PERMANENT,
                itself = true
            ).text
        ).isEqualTo("Gets")
    }

    @Test
    fun playerYou() {
        Assertions.assertThat(
            MagicInstanceSelector(
                selectorType = SelectorType.PLAYER,
                itself = true
            ).text
        ).isEqualTo("You")
    }

    @Test
    fun playerEachPlayer() {
        Assertions.assertThat(
            MagicInstanceSelector(
                selectorType = SelectorType.PLAYER
            ).text
        ).isEqualTo("Each player")
    }

    @Test
    fun playerEachOpponent() {
        Assertions.assertThat(
            MagicInstanceSelector(
                selectorType = SelectorType.PLAYER,
                controllerType = PlayerType.OPPONENT
            ).text
        ).isEqualTo("Each opponent")
    }
}