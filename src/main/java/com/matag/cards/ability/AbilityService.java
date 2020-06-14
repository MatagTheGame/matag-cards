package com.matag.cards.ability;

import com.matag.cards.ability.type.AbilityType;
import com.matag.cards.properties.PowerToughness;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;
import static java.util.Collections.singletonList;

@Component
public class AbilityService {
  public PowerToughness powerToughnessFromParameters(List<String> parameters) {
    return parameters.stream()
      .filter(parameter -> parameter.contains("/"))
      .map(PowerToughness::powerToughness)
      .findFirst()
      .orElse(PowerToughness.powerToughness("0/0"));
  }

  public PowerToughness powerToughnessFromParameter(String parameter) {
    return powerToughnessFromParameters(singletonList(parameter));
  }

  public int damageFromParameter(String parameter) {
    if (parameter.startsWith("DAMAGE:")) {
      return parseInt(parameter.replace("DAMAGE:", ""));
    }
    return 0;
  }

  public int lifeFromParameter(String parameter) {
    if (parameter.startsWith("LIFE:")) {
      return parseInt(parameter.replace("LIFE:", ""));
    }
    return 0;
  }

  public int controllerDamageFromParameter(String parameter) {
    if (parameter.startsWith("CONTROLLER_DAMAGE:")) {
      return parseInt(parameter.replace("CONTROLLER_DAMAGE:", ""));
    }
    return 0;
  }

  public boolean destroyedFromParameter(String parameter) {
    return parameter.equals(":DESTROYED");
  }

  public boolean tappedFromParameter(String parameter) {
    return parameter.equals(":TAPPED");
  }

  public boolean doesNotUntapNextTurnFromParameter(String parameter) {
    return parameter.equals(":DOES_NOT_UNTAP_NEXT_TURN");
  }

  public boolean returnToOwnerHandFromParameter(String parameter) {
    return parameter.equals(":RETURN_TO_OWNER_HAND");
  }

  public boolean untappedFromParameter(String parameter) {
    return parameter.equals(":UNTAPPED");
  }

  public int drawFromParameter(String parameter) {
    if (parameter.startsWith("DRAW:")) {
      return parseInt(parameter.replace("DRAW:", ""));
    }
    return 0;
  }

  public boolean controlledFromParameter(String parameter) {
    return parameter.equals(":CONTROLLED");
  }

  public int plus1CountersFromParameter(String parameter) {
    if (parameter.startsWith("PLUS_1_COUNTERS:")) {
      return parseInt(parameter.replace("PLUS_1_COUNTERS:", ""));
    }
    return 0;
  }

  public int minus1CountersFromParameter(String parameter) {
    if (parameter.startsWith("MINUS_1_COUNTERS:")) {
      return parseInt(parameter.replace("MINUS_1_COUNTERS:", ""));
    }
    return 0;
  }

  public AbilityType keywordCounterFromParameter(String parameter) {
    if (parameter.startsWith("KEYWORD_COUNTER:")) {
      return AbilityType.valueOf(parameter.replace("KEYWORD_COUNTER:", ""));
    }
    return null;
  }

  public static String parametersAsString(List<String> parameters) {
    String text = parameters.stream().map(AbilityService::parameterAsString).collect(Collectors.joining(", "));
    return replaceLast(text, ",", " and");
  }

  private static String parameterAsString(String parameter) {
    if (parameter == null) {
      return null;

    } else if (parameter.startsWith("DAMAGE:")) {
      return parameter.replace("DAMAGE:", "") + " damage";

    } else if (parameter.startsWith("CONTROLLER_DAMAGE:")) {
      return "to its controller " + parameter.replace("CONTROLLER_DAMAGE:", "") + " damage";

    } else if (parameter.equals(":DOES_NOT_UNTAP_NEXT_TURN")) {
      return "doesn't untap next turn";

    } else if (parameter.equals(":RETURN_TO_OWNER_HAND")) {
      return "returned to its owner's hand";

    } else if (parameter.startsWith("PLUS_1_COUNTERS:")) {
      return parameter.replace("PLUS_1_COUNTERS:", "") + " +1/+1 counters";

    } else if (parameter.startsWith("MINUS_1_COUNTERS:")) {
      return parameter.replace("MINUS_1_COUNTERS:", "") + " -1/-1 counters";

    } else if (parameter.startsWith("KEYWORD_COUNTER:")) {
      return "a " + parameter.replace("KEYWORD_COUNTER:", "").toLowerCase().replace("_", " ") + " counter";

    } else {
      return parameter.replace(":", "").toLowerCase();
    }
  }

  public static String replaceLast(String text, String regex, String replacement) {
    return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
  }
}
