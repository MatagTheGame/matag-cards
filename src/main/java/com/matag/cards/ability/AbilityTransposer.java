package com.matag.cards.ability;

import com.matag.cards.ability.selector.CardInstanceSelector;
import com.matag.cards.ability.trigger.Trigger;
import com.matag.cards.ability.trigger.TriggerSubtype;
import com.matag.cards.ability.trigger.TriggerType;
import com.matag.player.PlayerType;

import static com.matag.cards.ability.selector.SelectorType.PERMANENT;
import static com.matag.cards.ability.selector.SelectorType.SPELL;
import static com.matag.cards.ability.type.AbilityType.PROWESS;
import static com.matag.cards.ability.type.AbilityType.SELECTED_PERMANENTS_GET;
import static com.matag.cards.properties.Type.CREATURE;
import static java.util.Collections.singletonList;

public class AbilityTransposer {
  private static final CardInstanceSelector SELECTOR_IT = CardInstanceSelector.builder().selectorType(PERMANENT).itself(true).build();
  private static final CardInstanceSelector YOUR_NON_CREATURE_SPELL = CardInstanceSelector.builder().selectorType(SPELL).notOfType(singletonList(CREATURE)).controllerType(PlayerType.PLAYER).build();

  public static Ability transpose(Ability ability) {
    if (ability.getAbilityType() == PROWESS) {
      return new Ability(
          SELECTED_PERMANENTS_GET,
          null,
          SELECTOR_IT,
          singletonList("+1/+1"),
          Trigger.builder()
              .type(TriggerType.TRIGGERED_ABILITY)
              .subtype(TriggerSubtype.WHEN_CAST)
              .cardInstanceSelector(YOUR_NON_CREATURE_SPELL)
              .build(),
          null,
          false
      );
    }
    return ability;
  }
}
