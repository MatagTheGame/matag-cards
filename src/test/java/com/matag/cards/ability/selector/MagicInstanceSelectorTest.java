package com.matag.cards.ability.selector;

import static com.matag.cards.ability.selector.SelectorType.PERMANENT;
import static com.matag.cards.properties.Subtype.ZOMBIE;
import static com.matag.cards.properties.Type.CREATURE;
import static com.matag.player.PlayerType.OPPONENT;
import static com.matag.player.PlayerType.PLAYER;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class MagicInstanceSelectorTest {
  @Test
  public void creaturesYouControlGetText() {
    assertThat(MagicInstanceSelector.builder().selectorType(PERMANENT).ofType(singletonList(CREATURE)).controllerType(PLAYER).build().getText()).isEqualTo("Creatures you control get");
  }

  @Test
  public void zombiesYouControlGetText() {
    assertThat(MagicInstanceSelector.builder().selectorType(PERMANENT).ofType(singletonList(CREATURE)).ofSubtype(List.of(ZOMBIE)).controllerType(PLAYER).build().getText()).isEqualTo("Zombies you control get");
  }

  @Test
  public void otherCreaturesYouControlGetText() {
    assertThat(MagicInstanceSelector.builder().selectorType(PERMANENT).ofType(singletonList(CREATURE)).controllerType(PLAYER).others(true).build().getText()).isEqualTo("Other creatures you control get");
  }

  @Test
  public void allOtherCreaturesGetText() {
    assertThat(MagicInstanceSelector.builder().selectorType(PERMANENT).ofType(singletonList(CREATURE)).others(true).build().getText()).isEqualTo("Other creatures get");
  }

  @Test
  public void itGetsText() {
    assertThat(MagicInstanceSelector.builder().selectorType(PERMANENT).itself(true).build().getText()).isEqualTo("Gets");
  }

  @Test
  public void playerYou() {
    assertThat(MagicInstanceSelector.builder().selectorType(SelectorType.PLAYER).itself(true).build().getText()).isEqualTo("You");
  }

  @Test
  public void playerEachPlayer() {
    assertThat(MagicInstanceSelector.builder().selectorType(SelectorType.PLAYER).build().getText()).isEqualTo("Each player");
  }

  @Test
  public void playerEachOpponent() {
    assertThat(MagicInstanceSelector.builder().selectorType(SelectorType.PLAYER).controllerType(OPPONENT).build().getText()).isEqualTo("Each opponent");
  }
}