package com.matag.cards;

import com.matag.cards.properties.Color;
import com.matag.cards.properties.Type;

import java.util.Set;

import static com.matag.cards.properties.Cost.COLORLESS;

public class CardUtils {
  public static boolean isColorless(Card card) {
    return card.getCost().stream().noneMatch(cost -> cost != COLORLESS);
  }

  public static boolean isOfType(Card card, Type type) {
    return card.getTypes().contains(type);
  }

  public static boolean isNotOfType(Card card, Type type) {
    return !isOfType(card, type);
  }

  public static boolean isOfColor(Card card, Color color) {
    return card.getColors().contains(color);
  }

  public static boolean isOfOnlyAnyOfTheColors(Card card, Set<Color> colors) {
    if (card.getColors().isEmpty()) {
      return false;
    }

    for (Color color : card.getColors()) {
      if (!colors.contains(color)) {
        return false;
      }
    }
    return true;
  }
}
