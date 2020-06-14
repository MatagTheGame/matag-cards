package com.matag.cards.ability;

import com.matag.cards.ability.selector.SelectorType;
import com.matag.cards.ability.trigger.TriggerSubtype;
import com.matag.cards.ability.trigger.TriggerType;
import com.matag.cards.ability.type.AbilityType;
import com.matag.cards.properties.Type;
import com.matag.player.PlayerType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AbilityTransposerTest {

  @Test
  public void notTransposableAbility() {
    // Given
    Ability lifelink = Ability.builder().abilityType(AbilityType.LIFELINK).build();

    // When
    Ability transposed = AbilityTransposer.transpose(lifelink);

    // Then
    assertThat(transposed).isEqualTo(lifelink);
  }

  @Test
  public void transposeProwess() {
    // Given
    Ability prowess = Ability.builder().abilityType(AbilityType.PROWESS).build();

    // When
    Ability transposed = AbilityTransposer.transpose(prowess);

    // Then
    assertThat(transposed.getAbilityType()).isEqualTo(AbilityType.SELECTED_PERMANENTS_GET);
    assertThat(transposed.getCardInstanceSelector().getSelectorType()).isEqualTo(SelectorType.PERMANENT);
    assertThat(transposed.getCardInstanceSelector().isItself()).isTrue();
    assertThat(transposed.getParameters()).containsExactly("+1/+1");
    assertThat(transposed.getTrigger().getType()).isEqualTo(TriggerType.TRIGGERED_ABILITY);
    assertThat(transposed.getTrigger().getSubtype()).isEqualTo(TriggerSubtype.WHEN_CAST);
    assertThat(transposed.getTrigger().getCardInstanceSelector().getSelectorType()).isEqualTo(SelectorType.SPELL);
    assertThat(transposed.getTrigger().getCardInstanceSelector().getNotOfType()).containsExactly(Type.CREATURE);
    assertThat(transposed.getTrigger().getCardInstanceSelector().getControllerType()).isEqualTo(PlayerType.PLAYER);
  }
}