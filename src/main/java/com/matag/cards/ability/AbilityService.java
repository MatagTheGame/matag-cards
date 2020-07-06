package com.matag.cards.ability;

import com.matag.cards.ability.type.AbilityType;
import com.matag.cards.properties.PowerToughness;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.matag.language.StringUtils.replaceLast;
import static java.util.Collections.singletonList;

@Component
public class AbilityService {
  // permanent
  private static final String DAMAGE = "DAMAGE:";
  private static final String CONTROLLER_DAMAGE = "CONTROLLER_DAMAGE:";
  private static final String TAPPED = ":TAPPED";
  private static final String UNTAPPED = ":UNTAPPED";
  private static final String DOES_NOT_UNTAP_NEXT_TURN = ":DOES_NOT_UNTAP_NEXT_TURN";
  private static final String DESTROYED = ":DESTROYED";
  private static final String RETURN_TO_OWNER_HAND = ":RETURN_TO_OWNER_HAND";
  private static final String CONTROLLED = ":CONTROLLED";
  private static final String PLUS_1_COUNTERS = "PLUS_1_COUNTERS:";
  private static final String MINUS_1_COUNTERS = "MINUS_1_COUNTERS:";
  private static final String KEYWORD_COUNTER = "KEYWORD_COUNTER:";
  // player
  private static final String DRAW = "DRAW:";
  private static final String LIFE = "LIFE:";

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
    return getParameterIntValue(parameter, DAMAGE);
  }

  public int controllerDamageFromParameter(String parameter) {
    return getParameterIntValue(parameter, CONTROLLER_DAMAGE);
  }

  public boolean tappedFromParameter(String parameter) {
    return parameter.equals(TAPPED);
  }

  public boolean untappedFromParameter(String parameter) {
    return parameter.equals(UNTAPPED);
  }

  public boolean doesNotUntapNextTurnFromParameter(String parameter) {
    return parameter.equals(DOES_NOT_UNTAP_NEXT_TURN);
  }

  public boolean destroyedFromParameter(String parameter) {
    return parameter.equals(DESTROYED);
  }

  public boolean returnToOwnerHandFromParameter(String parameter) {
    return parameter.equals(RETURN_TO_OWNER_HAND);
  }

  public boolean controlledFromParameter(String parameter) {
    return parameter.equals(CONTROLLED);
  }

  public int plus1CountersFromParameter(String parameter) {
    return getParameterIntValue(parameter, PLUS_1_COUNTERS);
  }

  public int minus1CountersFromParameter(String parameter) {
    return getParameterIntValue(parameter, MINUS_1_COUNTERS);
  }

  public AbilityType keywordCounterFromParameter(String parameter) {
    return getParameterValue(parameter, KEYWORD_COUNTER)
        .map(AbilityType::valueOf)
        .orElse(null);
  }

  public int drawFromParameter(String parameter) {
    return getParameterIntValue(parameter, DRAW);
  }

  public int lifeFromParameter(String parameter) {
    return getParameterIntValue(parameter, LIFE);
  }

  public String parametersAsString(List<String> parameters) {
    var text = parameters.stream().map(this::safeParameterAsString).collect(Collectors.joining(", "));
    return replaceLast(text, ",", " and");
  }

  public String safeParameterAsString(String parameter) {
    try {
      return parameterAsString(parameter);
    } catch (Exception e) {
      return parameter.toLowerCase();
    }
  }

  public String parameterAsString(String parameter) throws RuntimeException {
    if (parameter.contains("/")) {
      return parameter;

    } else if (parameter.startsWith("DAMAGE:")) {
      return damageFromParameter(parameter) + " damage";

    } else if (parameter.startsWith("CONTROLLER_DAMAGE:")) {
      return "to its controller " + controllerDamageFromParameter(parameter) + " damage";

    } else if (tappedFromParameter(parameter)) {
      return "tapped";

    } else if (untappedFromParameter(parameter)) {
      return "untapped";

    } else if (doesNotUntapNextTurnFromParameter(parameter)) {
      return "doesn't untap next turn";

    } else if (destroyedFromParameter(parameter)) {
      return "destroyed";

    } else if (returnToOwnerHandFromParameter(parameter)) {
      return "returned to its owner's hand";

    } else if (controlledFromParameter(parameter)) {
      return "controlled";

    } else if (parameter.startsWith("PLUS_1_COUNTERS:")) {
      return plus1CountersFromParameter(parameter) + " +1/+1 counters";

    } else if (parameter.startsWith("MINUS_1_COUNTERS:")) {
      return minus1CountersFromParameter(parameter) + " -1/-1 counters";

    } else if (parameter.startsWith("KEYWORD_COUNTER:")) {
      return "a " + abilityParameterAsString(keywordCounterFromParameter(parameter)) + " counter";

    } else if (parameter.startsWith("LIFE:")) {
      var life = lifeFromParameter(parameter);
      return (life > 0 ? "gain " + life : "lose " + (-life)) + " life";

    } else if (parameter.startsWith("DRAW:")) {
      var draw = drawFromParameter(parameter);
      return "draw " + draw + " card" + (draw > 1 ? "s" : "");

    } else {
      return abilityParameterAsString(AbilityType.valueOf(parameter));
    }
  }

  private String abilityParameterAsString(AbilityType abilityType) {
    return abilityType.getText().substring(0, abilityType.getText().length() - 1).toLowerCase();
  }

  private Optional<String> getParameterValue(String parameter, String parameterType) {
    if (parameter.startsWith(parameterType)) {
      return Optional.of(parameter.replace(parameterType, ""));
    }
    return Optional.empty();
  }

  private int getParameterIntValue(String parameter, String parameterType) {
    return getParameterValue(parameter, parameterType).map(Integer::parseInt).orElse(0);
  }
}
