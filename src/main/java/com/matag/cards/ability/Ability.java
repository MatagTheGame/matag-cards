package com.matag.cards.ability;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.matag.cards.ability.selector.MagicInstanceSelector;
import com.matag.cards.ability.target.Target;
import com.matag.cards.ability.trigger.Trigger;
import com.matag.cards.ability.type.AbilityType;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonDeserialize(builder = Ability.AbilityBuilder.class)
@Builder
public class Ability {
  protected AbilityType abilityType;
  protected List<Target> targets;
  protected MagicInstanceSelector magicInstanceSelector;
  protected List<String> parameters;
  protected Trigger trigger;
  protected Ability ability;
  protected boolean sorcerySpeed;
  protected boolean optional;

  public Ability(AbilityType abilityType, List<Target> targets, MagicInstanceSelector magicInstanceSelector,
                 List<String> parameters, Trigger trigger, Ability ability, boolean sorcerySpeed, boolean optional) {
    this.abilityType = abilityType;
    this.targets = targets != null ? targets : new ArrayList<>();
    this.magicInstanceSelector = magicInstanceSelector;
    this.parameters = parameters != null ? parameters : new ArrayList<>();
    this.trigger = trigger;
    this.ability = ability;
    this.sorcerySpeed = sorcerySpeed;
    this.optional = optional;
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class AbilityBuilder {}
}
