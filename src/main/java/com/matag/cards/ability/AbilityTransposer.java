package com.matag.cards.ability;

import static com.matag.cards.ability.selector.SelectorType.PERMANENT;
import static com.matag.cards.ability.selector.SelectorType.SPELL;
import static com.matag.cards.ability.type.AbilityType.PROWESS;
import static com.matag.cards.ability.type.AbilityType.SELECTED_PERMANENTS_GET;
import static com.matag.cards.properties.Type.CREATURE;
import static java.util.Collections.singletonList;

import com.matag.cards.ability.selector.MagicInstanceSelector;
import com.matag.cards.ability.trigger.Trigger;
import com.matag.cards.ability.trigger.TriggerSubtype;
import com.matag.cards.ability.trigger.TriggerType;
import com.matag.player.PlayerType;

public class AbilityTransposer {
    private static final MagicInstanceSelector SELECTOR_IT = MagicInstanceSelector.builder().selectorType(PERMANENT).itself(true).build();
    private static final MagicInstanceSelector YOUR_NON_CREATURE_SPELL = MagicInstanceSelector.builder().selectorType(SPELL).notOfType(singletonList(CREATURE)).controllerType(PlayerType.PLAYER).build();

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
                            .magicInstanceSelector(YOUR_NON_CREATURE_SPELL)
                            .build(),
                    null,
                    false,
                    false
            );
        }
        return ability;
    }
}
