package com.matag.cards.ability.trigger;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.matag.cards.ability.selector.MagicInstanceSelector;
import com.matag.cards.properties.Cost;

import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = Trigger.TriggerBuilder.class)
@Builder
public class Trigger {
  TriggerType type;
  TriggerSubtype subtype;
  List<Cost> cost;
  MagicInstanceSelector magicInstanceSelector;

  @JsonPOJOBuilder(withPrefix = "")
  public static class TriggerBuilder {}

  public static Trigger castTrigger() {
    return new Trigger(TriggerType.CAST, null, null, null);
  }

  public static Trigger manaAbilityTrigger() {
    return new Trigger(TriggerType.MANA_ABILITY, null, null, null);
  }

  public static Trigger triggeredAbility(TriggerSubtype triggerSubtype, MagicInstanceSelector magicInstanceSelector) {
    return new Trigger(TriggerType.TRIGGERED_ABILITY, triggerSubtype, null, magicInstanceSelector);
  }

  public static Trigger activatedAbility(List<Cost> cost) {
    return new Trigger(TriggerType.ACTIVATED_ABILITY, null, cost, null);
  }

  public static Trigger staticAbility() {
    return new Trigger(TriggerType.STATIC, null, null, null);
  }
}
