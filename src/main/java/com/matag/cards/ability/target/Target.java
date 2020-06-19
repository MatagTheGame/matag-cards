package com.matag.cards.ability.target;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.matag.cards.ability.selector.MagicInstanceSelector;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = Target.TargetBuilder.class)
@Builder(toBuilder = true)
public class Target {
  private final MagicInstanceSelector magicInstanceSelector;
  private final boolean optional;
  private final boolean other;

  @JsonPOJOBuilder(withPrefix = "")
  public static class TargetBuilder {

  }
}
