package com.matag.cards.ability.selector;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.matag.cards.ability.type.AbilityType;
import com.matag.cards.properties.Color;
import com.matag.cards.properties.Subtype;
import com.matag.cards.properties.Type;
import com.matag.language.Plural;
import com.matag.player.PlayerType;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Value
@JsonDeserialize(builder = CardInstanceSelector.CardInstanceSelectorBuilder.class)
@Builder(toBuilder = true)
public class CardInstanceSelector {
  SelectorType selectorType;
  List<Type> ofType;
  List<Type> notOfType;
  List<Subtype> ofSubtype;
  List<Subtype> notOfSubtype;
  AbilityType withAbilityType;
  List<Color> ofColors;
  boolean colorless;
  boolean multicolor;
  PowerToughnessConstraint powerToughnessConstraint;
  PlayerType controllerType;
  List<StatusType> statusTypes;
  boolean others;
  boolean itself;
  boolean nonToken;
  boolean currentEnchanted;
  boolean attacking;
  boolean attacked;
  boolean blocking;
  boolean blocked;
  TurnStatusType turnStatusType;
  boolean historic;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CardInstanceSelectorBuilder {

  }

  @JsonIgnore
  public String getText() {
    StringBuilder stringBuilder = new StringBuilder();

    if (selectorType == SelectorType.PERMANENT) {

      if (itself) {
        stringBuilder.append("gets");

      } else {
        if (others) {
          stringBuilder.append("other ");
        }

        if (currentEnchanted) {
          stringBuilder.append("enchanted ");
        }

        if (ofSubtype != null) {
          stringBuilder.append(ofSubtype.stream()
              .map(Objects::toString)
              .map(Plural::plural)
              .map(this::spaced)
              .collect(Collectors.joining(", ")));

        } else if (ofType != null) {
          stringBuilder.append(ofType.stream()
              .map(Objects::toString)
              .map(Plural::plural)
              .map(this::spaced)
              .collect(Collectors.joining(", ")));
        }

        if (controllerType != null) {
          if (controllerType == PlayerType.PLAYER) {
            stringBuilder.append("you control ");
          } else {
            stringBuilder.append("opponent controls ");
          }
        }

        stringBuilder.append("get");
      }

    } else if (selectorType == SelectorType.PLAYER) {
      if (itself) {
        return "You";

      } else {
        if (controllerType == null || controllerType == PlayerType.PLAYER) {
          return "Each player";
        } else {
          return "Each opponent";
        }
      }
    }

    String str = stringBuilder.toString();
    str = str.toLowerCase();
    str = str.isEmpty() ? str : str.substring(0, 1).toUpperCase() + str.substring(1);

    return str.trim();
  }

  private String spaced(String word) {
    return word + " ";
  }
}
