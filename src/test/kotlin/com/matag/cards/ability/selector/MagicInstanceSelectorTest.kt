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
            MagicInstanceSelector.builder().selectorType(SelectorType.PERMANENT)
                .ofType(mutableListOf<Type?>(Type.CREATURE)).controllerType(PlayerType.PLAYER).build().getText()
        ).isEqualTo("Creatures you control get")
    }

    @Test
    fun zombiesYouControlGetText() {
        Assertions.assertThat(
            MagicInstanceSelector.builder().selectorType(SelectorType.PERMANENT)
                .ofType(mutableListOf<Type?>(Type.CREATURE)).ofSubtype(List.of<Subtype?>(Subtype.ZOMBIE))
                .controllerType(PlayerType.PLAYER).build().getText()
        ).isEqualTo("Zombies you control get")
    }

    @Test
    fun otherCreaturesYouControlGetText() {
        Assertions.assertThat(
            MagicInstanceSelector.builder().selectorType(SelectorType.PERMANENT)
                .ofType(mutableListOf<Type?>(Type.CREATURE)).controllerType(PlayerType.PLAYER).others(true).build()
                .getText()
        ).isEqualTo("Other creatures you control get")
    }

    @Test
    fun allOtherCreaturesGetText() {
        Assertions.assertThat(
            MagicInstanceSelector.builder().selectorType(SelectorType.PERMANENT)
                .ofType(mutableListOf<Type?>(Type.CREATURE)).others(true).build().getText()
        ).isEqualTo("Other creatures get")
    }

    @Test
    fun itGetsText() {
        Assertions.assertThat(
            MagicInstanceSelector.builder().selectorType(SelectorType.PERMANENT).itself(true).build().getText()
        ).isEqualTo("Gets")
    }

    @Test
    fun playerYou() {
        Assertions.assertThat(
            MagicInstanceSelector.builder().selectorType(SelectorType.PLAYER).itself(true).build().getText()
        ).isEqualTo("You")
    }

    @Test
    fun playerEachPlayer() {
        Assertions.assertThat(MagicInstanceSelector.builder().selectorType(SelectorType.PLAYER).build().getText())
            .isEqualTo("Each player")
    }

    @Test
    fun playerEachOpponent() {
        Assertions.assertThat(
            MagicInstanceSelector.builder().selectorType(SelectorType.PLAYER).controllerType(PlayerType.OPPONENT)
                .build().getText()
        ).isEqualTo("Each opponent")
    }
}