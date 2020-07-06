package com.matag.cards;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.matag.cards.ability.Ability;
import com.matag.cards.properties.*;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Value
@JsonDeserialize(builder = Card.CardBuilder.class)
@Builder(toBuilder = true)
public class Card {
  String name;
  String imageUrl;
  @Builder.Default TreeSet<Color> colors = new TreeSet<>();
  @Builder.Default List<Cost> cost = new ArrayList<>();
  @Builder.Default TreeSet<Type> types = new TreeSet<>();
  @Builder.Default TreeSet<Subtype> subtypes = new TreeSet<>();
  Rarity rarity;
  String ruleText;
  int power;
  int toughness;
  @Builder.Default List<Ability> abilities = new ArrayList<>();

  @JsonPOJOBuilder(withPrefix = "")
  public static class CardBuilder {}

}
