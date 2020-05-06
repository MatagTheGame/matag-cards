package com.matag.cards.sets;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.util.TreeSet;

@Value
@JsonDeserialize(builder = MtgSet.MtgSetBuilder.class)
@Builder(toBuilder = true)
public class MtgSet {
  private final String code;
  private final String name;
  private final TreeSet<String> cards;

  @JsonPOJOBuilder(withPrefix = "")
  public static class MtgSetBuilder {

  }
}
