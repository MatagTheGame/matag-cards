package com.matag.cards.ability;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.matag.cards.ability.selector.MagicInstanceSelector;
import com.matag.cards.ability.target.Target;
import com.matag.cards.ability.trigger.Trigger;
import com.matag.cards.ability.type.AbilityType;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@JsonDeserialize(builder = Ability.AbilityBuilder.class)
@Builder
public class Ability {
  AbilityType abilityType;
  List<Target> targets;
  MagicInstanceSelector magicInstanceSelector;
  List<String> parameters;
  Trigger trigger;
  Ability ability;
  boolean sorcerySpeed;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AbilityBuilder {}
}
