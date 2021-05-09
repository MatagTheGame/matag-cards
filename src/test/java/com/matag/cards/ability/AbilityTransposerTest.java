package com.matag.cards.ability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.matag.cards.ability.selector.SelectorType;
import com.matag.cards.ability.trigger.TriggerSubtype;
import com.matag.cards.ability.trigger.TriggerType;
import com.matag.cards.ability.type.AbilityType;
import com.matag.cards.properties.Type;
import com.matag.player.PlayerType;

public class AbilityTransposerTest {

  @Test
  public void notTransposableAbility() {
    // Given
    var lifelink = Ability.builder().abilityType(AbilityType.LIFELINK).build();

    // When
    var transposed = AbilityTransposer.transpose(lifelink);

    // Then
    assertThat(transposed).isEqualTo(lifelink);
  }

  @Test
  public void transposeProwess() {
    // Given
    var prowess = Ability.builder().abilityType(AbilityType.PROWESS).build();

    // When
    var transposed = AbilityTransposer.transpose(prowess);

    // Then
    assertThat(transposed.getAbilityType()).isEqualTo(AbilityType.SELECTED_PERMANENTS_GET);
    assertThat(transposed.getMagicInstanceSelector().getSelectorType()).isEqualTo(SelectorType.PERMANENT);
    assertThat(transposed.getMagicInstanceSelector().isItself()).isTrue();
    assertThat(transposed.getParameters()).containsExactly("+1/+1");
    assertThat(transposed.getTrigger().getType()).isEqualTo(TriggerType.TRIGGERED_ABILITY);
    assertThat(transposed.getTrigger().getSubtype()).isEqualTo(TriggerSubtype.WHEN_CAST);
    assertThat(transposed.getTrigger().getMagicInstanceSelector().getSelectorType()).isEqualTo(SelectorType.SPELL);
    assertThat(transposed.getTrigger().getMagicInstanceSelector().getNotOfType()).containsExactly(Type.CREATURE);
    assertThat(transposed.getTrigger().getMagicInstanceSelector().getControllerType()).isEqualTo(PlayerType.PLAYER);
  }
}