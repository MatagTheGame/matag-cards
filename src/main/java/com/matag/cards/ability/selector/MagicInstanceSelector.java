package com.matag.cards.ability.selector;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

@Value
@JsonDeserialize(builder = MagicInstanceSelector.MagicInstanceSelectorBuilder.class)
@Builder
public class MagicInstanceSelector {
  SelectorType selectorType;
  List<Type> ofType;
  List<Type> ofAllTypes;
  List<Type> notOfType;
  List<Subtype> ofSubtype;
  List<Subtype> notOfSubtype;
  AbilityType withAbilityType;
  AbilityType withoutAbilityType;
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
  TurnStatusType turnStatusType;
  boolean historic;

  @JsonPOJOBuilder(withPrefix = "")
  public static class MagicInstanceSelectorBuilder {}

  @JsonIgnore
  public String getText() {
    var stringBuilder = new StringBuilder();

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

    var str = stringBuilder.toString();
    str = str.toLowerCase();
    str = str.isEmpty() ? str : str.substring(0, 1).toUpperCase() + str.substring(1);

    return str.trim();
  }

  private String spaced(String word) {
    return word + " ";
  }
}
