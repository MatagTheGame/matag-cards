package com.matag.cards.ability;

import com.matag.cards.ability.type.AbilityType;
import com.matag.cards.properties.PowerToughness;
import org.junit.Test;

import java.util.List;

import static com.matag.cards.ability.type.AbilityType.MENACE;
import static org.assertj.core.api.Assertions.assertThat;

public class AbilityServiceTest {
  private final AbilityService abilityService = new AbilityService();

  @Test
  public void testPowerAndToughnessFromParameter() {
    // Given
    var parameter = "+2/+2";

    // When
    var PowerToughness = abilityService.powerToughnessFromParameter(parameter);

    // Then
    assertThat(PowerToughness).isEqualTo(new PowerToughness(2, 2));
  }

  @Test
  public void testPowerAndToughnessFromParameterAbsent() {
    // Given
    var parameter = "TRAMPLE";

    // When
    var PowerToughness = abilityService.powerToughnessFromParameter(parameter);

    // Then
    assertThat(PowerToughness).isEqualTo(new PowerToughness(0, 0));
  }

  @Test
  public void testPowerAndToughnessFromParameters() {
    // Given
    var parameters = List.of("TRAMPLE", "+2/+2", "HASTE");

    // When
    var PowerToughness = abilityService.powerToughnessFromParameters(parameters);

    // Then
    assertThat(PowerToughness).isEqualTo(new PowerToughness(2, 2));
  }

  @Test
  public void testPowerAndToughnessFromParametersAbsent() {
    // Given
    var parameter = "TRAMPLE";

    // When
    var PowerToughness = abilityService.powerToughnessFromParameter(parameter);

    // Then
    assertThat(PowerToughness).isEqualTo(new PowerToughness(0, 0));
  }

  @Test
  public void testDamageFromParameter() {
    // Given
    var parameter = "DAMAGE:4";

    // When
    var damage = abilityService.damageFromParameter(parameter);

    // Then
    assertThat(damage).isEqualTo(4);
  }

  @Test
  public void testDamageFromParameterAbsent() {
    // When
    var damage = abilityService.damageFromParameter("TRAMPLE");

    // Then
    assertThat(damage).isEqualTo(0);
  }

  @Test
  public void testControllerDamageFromParameter() {
    // Given
    var parameter = "CONTROLLER_DAMAGE:5";

    // When
    var damage = abilityService.controllerDamageFromParameter(parameter);

    // Then
    assertThat(damage).isEqualTo(5);
  }

  @Test
  public void testTappedFromParameter() {
    // Given
    var parameter = ":TAPPED";

    // When
    var tapped = abilityService.tappedFromParameter(parameter);

    // Then
    assertThat(tapped).isTrue();
  }

  @Test
  public void testUntappedFromParameter() {
    // Given
    var parameter = ":UNTAPPED";

    // When
    var untapped = abilityService.untappedFromParameter(parameter);

    // Then
    assertThat(untapped).isTrue();
  }

  @Test
  public void testDoesNotUntapNextTurnFromParameter() {
    // Given
    var parameter = ":DOES_NOT_UNTAP_NEXT_TURN";

    // When
    var doesNotUntapNextTurn = abilityService.doesNotUntapNextTurnFromParameter(parameter);

    // Then
    assertThat(doesNotUntapNextTurn).isTrue();
  }

  @Test
  public void testDestroyedFromParameter() {
    // Given
    var parameter = ":DESTROYED";

    // When
    var destroyed = abilityService.destroyedFromParameter(parameter);

    // Then
    assertThat(destroyed).isTrue();
  }

  @Test
  public void testReturnedToOwnerHandFromParameter() {
    // Given
    var parameter = ":RETURN_TO_OWNER_HAND";

    // When
    var returnToOwnerHand = abilityService.returnToOwnerHandFromParameter(parameter);

    // Then
    assertThat(returnToOwnerHand).isTrue();
  }

  @Test
  public void testControlledFromParameter() {
    // Given
    var parameter = ":CONTROLLED";

    // When
    var controlled = abilityService.controlledFromParameter(parameter);

    // Then
    assertThat(controlled).isTrue();
  }

  @Test
  public void testPlus1CountersFromParameter() {
    // Given
    var parameter = "PLUS_1_COUNTERS:2";

    // When
    var counters = abilityService.plus1CountersFromParameter(parameter);

    // Then
    assertThat(counters).isEqualTo(2);
  }

  @Test
  public void testMinus1CountersFromParameter() {
    // Given
    var parameter = "MINUS_1_COUNTERS:3";

    // When
    var counters = abilityService.minus1CountersFromParameter(parameter);

    // Then
    assertThat(counters).isEqualTo(3);
  }

  @Test
  public void testKeywordCounterFromParameter() {
    // Given
    var parameter = "KEYWORD_COUNTER:MENACE";

    // When
    var keywordAbility = abilityService.keywordCounterFromParameter(parameter);

    // Then
    assertThat(keywordAbility).isEqualTo(MENACE);
  }


  @Test
  public void testDrawFromParameter() {
    // Given
    var parameter = "DRAW:2";

    // When
    var draw = abilityService.drawFromParameter(parameter);

    // Then
    assertThat(draw).isEqualTo(2);
  }

  @Test
  public void testDrawFromParameterAbsent() {
    // When
    var draw = abilityService.drawFromParameter("TRAMPLE");

    // Then
    assertThat(draw).isEqualTo(0);
  }

  @Test
  public void testLifeFromParameter() {
    // Given
    var parameter = "LIFE:3";

    // When
    var life = abilityService.lifeFromParameter(parameter);

    // Then
    assertThat(life).isEqualTo(3);
  }

  @Test
  public void testPermanentParametersAsString() {
    // Given
    var parameters = List.of("+2/+2", "TRAMPLE", "DAMAGE:2", "HASTE", "CONTROLLER_DAMAGE:3", ":TAPPED", ":DOES_NOT_UNTAP_NEXT_TURN", ":DESTROYED", ":RETURN_TO_OWNER_HAND", "PLUS_1_COUNTERS:2", "KEYWORD_COUNTER:MENACE");

    // When
    var parametersAsString = abilityService.parametersAsString(parameters);

    // Then
    assertThat(parametersAsString).isEqualTo("+2/+2, trample, 2 damage, haste, to its controller 3 damage, tapped, doesn't untap next turn, destroyed, returned to its owner's hand, 2 +1/+1 counters and a menace counter");
  }

  @Test
  public void testPlayerParametersAsString() {
    // Given
    var parameters = List.of("LIFE:2", "LIFE:-3", "DRAW:1", "DRAW:2");

    // When
    var parametersAsString = abilityService.parametersAsString(parameters);

    // Then
    assertThat(parametersAsString).isEqualTo("gain 2 life, lose 3 life, draw 1 card and draw 2 cards");
  }
}