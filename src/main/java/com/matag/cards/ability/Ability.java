package com.matag.cards.ability;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.matag.cards.ability.selector.MagicInstanceSelector;
import com.matag.cards.ability.target.Target;
import com.matag.cards.ability.trigger.Trigger;
import com.matag.cards.ability.type.AbilityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonDeserialize(builder = Ability.AbilityBuilder.class)
@Builder
@AllArgsConstructor
public class Ability {
  protected AbilityType abilityType;
  @Builder.Default protected List<Target> targets = new ArrayList<>();
  protected MagicInstanceSelector magicInstanceSelector;
  @Builder.Default protected List<String> parameters = new ArrayList<>();
  protected Trigger trigger;
  protected Ability ability;
  protected boolean sorcerySpeed;

  @JsonPOJOBuilder(withPrefix = "")
  public static class AbilityBuilder {}
}
