package com.matag.cards.ability.selector;

import org.junit.Test;

import static com.matag.cards.ability.selector.SelectorType.PERMANENT;
import static com.matag.cards.properties.Type.CREATURE;
import static com.matag.player.PlayerType.OPPONENT;
import static com.matag.player.PlayerType.PLAYER;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class CardInstanceSelectorTest {
  @Test
  public void creaturesYouControlGetText() {
    assertThat(CardInstanceSelector.builder().selectorType(PERMANENT).ofType(singletonList(CREATURE)).controllerType(PLAYER).build().getText()).isEqualTo("Creatures you control get");
  }

  @Test
  public void otherCreaturesYouControlGetText() {
    assertThat(CardInstanceSelector.builder().selectorType(PERMANENT).ofType(singletonList(CREATURE)).controllerType(PLAYER).others(true).build().getText()).isEqualTo("Other creatures you control get");
  }

  @Test
  public void allOtherCreaturesGetText() {
    assertThat(CardInstanceSelector.builder().selectorType(PERMANENT).ofType(singletonList(CREATURE)).others(true).build().getText()).isEqualTo("Other creatures get");
  }

  @Test
  public void itGetsText() {
    assertThat(CardInstanceSelector.builder().selectorType(PERMANENT).itself(true).build().getText()).isEqualTo("Gets");
  }

  @Test
  public void playerYou() {
    assertThat(CardInstanceSelector.builder().selectorType(SelectorType.PLAYER).itself(true).build().getText()).isEqualTo("You");
  }

  @Test
  public void playerEachPlayer() {
    assertThat(CardInstanceSelector.builder().selectorType(SelectorType.PLAYER).build().getText()).isEqualTo("Each player");
  }

  @Test
  public void playerEachOpponent() {
    assertThat(CardInstanceSelector.builder().selectorType(SelectorType.PLAYER).controllerType(OPPONENT).build().getText()).isEqualTo("Each opponent");
  }
}