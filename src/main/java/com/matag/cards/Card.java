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
  TreeSet<Color> colors;
  List<Cost> cost;
  TreeSet<Type> types;
  TreeSet<Subtype> subtypes;
  Rarity rarity;
  String ruleText;
  int power;
  int toughness;
  List<Ability> abilities;

  public Card(String name, String imageUrl, TreeSet<Color> colors, List<Cost> cost, TreeSet<Type> types,
              TreeSet<Subtype> subtypes, Rarity rarity, String ruleText, int power, int toughness, List<Ability> abilities) {
    this.name = name;
    this.imageUrl = imageUrl;
    this.colors = colors != null ? colors : new TreeSet<>();
    this.cost = cost != null ? cost : new ArrayList<>();
    this.types = types != null ? types : new TreeSet<>();
    this.subtypes = subtypes != null ? subtypes : new TreeSet<>();
    this.rarity = rarity;
    this.ruleText = ruleText != null ? ruleText : "";
    this.power = power;
    this.toughness = toughness;
    this.abilities = abilities != null ? abilities : new ArrayList<>();
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class CardBuilder {}

}
