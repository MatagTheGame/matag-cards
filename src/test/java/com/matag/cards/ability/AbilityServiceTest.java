package com.matag.cards.ability;

import com.matag.cards.ability.type.AbilityType;
import com.matag.cards.properties.PowerToughness;
import org.junit.Test;

import java.util.List;

import static com.matag.cards.ability.type.AbilityType.MENACE;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class AbilityServiceTest {
  private final AbilityService abilityService = new AbilityService();

  @Test
  public void testPowerAndToughnessFromParameter() {
    // Given
    String parameter = "+2/+2";

    // When
    PowerToughness PowerToughness = abilityService.powerToughnessFromParameter(parameter);

    // Then
    assertThat(PowerToughness).isEqualTo(new PowerToughness(2, 2));
  }

  @Test
  public void testPowerAndToughnessFromParameterAbsent() {
    // Given
    String parameter = "TRAMPLE";

    // When
    PowerToughness PowerToughness = abilityService.powerToughnessFromParameter(parameter);

    // Then
    assertThat(PowerToughness).isEqualTo(new PowerToughness(0, 0));
  }

  @Test
  public void testPowerAndToughnessFromParameters() {
    // Given
    List<String> parameters = asList("TRAMPLE", "+2/+2", "HASTE");

    // When
    PowerToughness PowerToughness = abilityService.powerToughnessFromParameters(parameters);

    // Then
    assertThat(PowerToughness).isEqualTo(new PowerToughness(2, 2));
  }

  @Test
  public void testPowerAndToughnessFromParametersAbsent() {
    // Given
    String parameter = "TRAMPLE";

    // When
    PowerToughness PowerToughness = abilityService.powerToughnessFromParameter(parameter);

    // Then
    assertThat(PowerToughness).isEqualTo(new PowerToughness(0, 0));
  }

  @Test
  public void testDamageFromParameter() {
    // Given
    String parameter = "DAMAGE:4";

    // When
    int damage = abilityService.damageFromParameter(parameter);

    // Then
    assertThat(damage).isEqualTo(4);
  }

  @Test
  public void testDamageFromParameterAbsent() {
    // When
    int damage = abilityService.damageFromParameter("TRAMPLE");

    // Then
    assertThat(damage).isEqualTo(0);
  }

  @Test
  public void testControllerDamageFromParameter() {
    // Given
    String parameter = "CONTROLLER_DAMAGE:5";

    // When
    int damage = abilityService.controllerDamageFromParameter(parameter);

    // Then
    assertThat(damage).isEqualTo(5);
  }

  @Test
  public void testTappedFromParameter() {
    // Given
    String parameter = ":TAPPED";

    // When
    boolean tapped = abilityService.tappedFromParameter(parameter);

    // Then
    assertThat(tapped).isTrue();
  }

  @Test
  public void testUntappedFromParameter() {
    // Given
    String parameter = ":UNTAPPED";

    // When
    boolean untapped = abilityService.untappedFromParameter(parameter);

    // Then
    assertThat(untapped).isTrue();
  }

  @Test
  public void testDoesNotUntapNextTurnFromParameter() {
    // Given
    String parameter = ":DOES_NOT_UNTAP_NEXT_TURN";

    // When
    boolean doesNotUntapNextTurn = abilityService.doesNotUntapNextTurnFromParameter(parameter);

    // Then
    assertThat(doesNotUntapNextTurn).isTrue();
  }

  @Test
  public void testDestroyedFromParameter() {
    // Given
    String parameter = ":DESTROYED";

    // When
    boolean destroyed = abilityService.destroyedFromParameter(parameter);

    // Then
    assertThat(destroyed).isTrue();
  }

  @Test
  public void testReturnedToOwnerHandFromParameter() {
    // Given
    String parameter = ":RETURN_TO_OWNER_HAND";

    // When
    boolean returnToOwnerHand = abilityService.returnToOwnerHandFromParameter(parameter);

    // Then
    assertThat(returnToOwnerHand).isTrue();
  }

  @Test
  public void testControlledFromParameter() {
    // Given
    String parameter = ":CONTROLLED";

    // When
    boolean controlled = abilityService.controlledFromParameter(parameter);

    // Then
    assertThat(controlled).isTrue();
  }

  @Test
  public void testPlus1CountersFromParameter() {
    // Given
    String parameter = "PLUS_1_COUNTERS:2";

    // When
    int counters = abilityService.plus1CountersFromParameter(parameter);

    // Then
    assertThat(counters).isEqualTo(2);
  }

  @Test
  public void testMinus1CountersFromParameter() {
    // Given
    String parameter = "MINUS_1_COUNTERS:3";

    // When
    int counters = abilityService.minus1CountersFromParameter(parameter);

    // Then
    assertThat(counters).isEqualTo(3);
  }

  @Test
  public void testKeywordCounterFromParameter() {
    // Given
    String parameter = "KEYWORD_COUNTER:MENACE";

    // When
    AbilityType keywordAbility = abilityService.keywordCounterFromParameter(parameter);

    // Then
    assertThat(keywordAbility).isEqualTo(MENACE);
  }


  @Test
  public void testDrawFromParameter() {
    // Given
    String parameter = "DRAW:2";

    // When
    int draw = abilityService.drawFromParameter(parameter);

    // Then
    assertThat(draw).isEqualTo(2);
  }

  @Test
  public void testDrawFromParameterAbsent() {
    // When
    int draw = abilityService.drawFromParameter("TRAMPLE");

    // Then
    assertThat(draw).isEqualTo(0);
  }

  @Test
  public void testLifeFromParameter() {
    // Given
    String parameter = "LIFE:3";

    // When
    int life = abilityService.lifeFromParameter(parameter);

    // Then
    assertThat(life).isEqualTo(3);
  }

  @Test
  public void testPermanentParametersAsString() {
    // Given
    List<String> parameters = asList("+2/+2", "TRAMPLE", "DAMAGE:2", "HASTE", "CONTROLLER_DAMAGE:3", ":TAPPED", ":DOES_NOT_UNTAP_NEXT_TURN", ":DESTROYED", ":RETURN_TO_OWNER_HAND", "PLUS_1_COUNTERS:2", "KEYWORD_COUNTER:MENACE");

    // When
    String parametersAsString = abilityService.parametersAsString(parameters);

    // Then
    assertThat(parametersAsString).isEqualTo("+2/+2, trample, 2 damage, haste, to its controller 3 damage, tapped, doesn't untap next turn, destroyed, returned to its owner's hand, 2 +1/+1 counters and a menace counter");
  }

  @Test
  public void testPlayerParametersAsString() {
    // Given
    List<String> parameters = asList("LIFE:2", "LIFE:-3", "DRAW:1", "DRAW:2");

    // When
    String parametersAsString = abilityService.parametersAsString(parameters);

    // Then
    assertThat(parametersAsString).isEqualTo("gain 2 life, lose 3 life, draw 1 card and draw 2 cards");
  }
}