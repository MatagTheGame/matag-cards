package com.matag.cards;

import static com.matag.cards.ability.type.AbilityType.SELECTED_PERMANENTS_GET;
import static com.matag.cards.ability.type.AbilityType.THAT_TARGETS_GET;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.matag.cards.ability.Ability;
import com.matag.cards.ability.AbilityService;
import com.matag.cards.ability.selector.MagicInstanceSelector;
import com.matag.cards.ability.selector.SelectorType;
import com.matag.cards.ability.target.Target;
import com.matag.cards.ability.trigger.Trigger;
import com.matag.cards.properties.Color;
import com.matag.cards.properties.Cost;
import com.matag.cards.properties.Rarity;
import com.matag.cards.properties.Subtype;
import com.matag.cards.properties.Type;

@RunWith(SpringRunner.class)
@Import(CardsConfiguration.class)
public class CardsTest {

  @Autowired
  private Cards cards;

  @Autowired
  private AbilityService abilityService;

  @Test
  public void shouldLoadAllCards() {
    assertThat(cards.getAll()).isNotEmpty();

    for (var card : cards.getAll()) {
      validateCard(card);
    }
  }

  @Test
  public void shouldLoadACardWithoutAbilities() {
    var card = cards.get("Feral Maaka");
    assertThat(card.getName()).isEqualTo("Feral Maaka");
    assertThat(card.getColors()).containsExactly(Color.RED);
    assertThat(card.getCost()).containsExactly(Cost.RED, Cost.ANY);
    assertThat(card.getTypes()).containsExactly(Type.CREATURE);
    assertThat(card.getSubtypes()).containsExactlyInAnyOrder(Subtype.CAT);
    assertThat(card.getRarity()).isEqualTo(Rarity.COMMON);
    assertThat(card.getRuleText()).isNullOrEmpty();
    assertThat(card.getPower()).isEqualTo(2);
    assertThat(card.getToughness()).isEqualTo(2);
  }

  @Test
  public void shouldLoadACardWithAbilities() {
    var card = cards.get("Act of Treason");
    assertThat(card.getName()).isEqualTo("Act of Treason");
    assertThat(card.getColors()).containsExactly(Color.RED);
    assertThat(card.getCost()).containsExactly(Cost.RED, Cost.ANY, Cost.ANY);
    assertThat(card.getTypes()).containsExactly(Type.SORCERY);
    assertThat(card.getSubtypes()).isNullOrEmpty();
    assertThat(card.getRarity()).isEqualTo(Rarity.COMMON);
    assertThat(card.getRuleText()).isEqualTo("Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.");
    assertThat(card.getPower()).isNull();
    assertThat(card.getToughness()).isNull();
    assertThat(card.getAbilities()).hasSize(1);
    assertThat(card.getAbilities().get(0)).isEqualTo(
      Ability.builder()
        .abilityType(THAT_TARGETS_GET)
        .targets(singletonList(Target.builder()
          .magicInstanceSelector(MagicInstanceSelector.builder()
            .selectorType(SelectorType.PERMANENT)
            .ofType(singletonList(Type.CREATURE))
            .build())
          .build()))
        .parameters(asList(":CONTROLLED", ":UNTAPPED", "HASTE"))
        .trigger(Trigger.castTrigger())
        .build()
    );
  }

  private void validateCard(Card card) {
    var name = card.getName();
    if (card.getImageUrl() == null) {
      throw new RuntimeException("Card '" + name + "' does not have an imageUrl. Run cardImageLinker");
    }

    if (card.getTypes().isEmpty()) {
      throw new RuntimeException("Card '" + name + "' does not have a type. Remove the image and run cardImageLinker");
    }

    if (card.getAbilities() != null) {
        card.getAbilities().stream()
                .filter(ability -> ability.getAbilityType().equals(THAT_TARGETS_GET))
                .forEach(ability -> {
                    if (ability.getTargets().isEmpty()) {
                        throw new RuntimeException("Card '" + name + "' is missing targets");
                    }

                    validateParameters(name, ability.getParameters());
                });

        card.getAbilities().stream()
                .filter(ability -> ability.getAbilityType().equals(SELECTED_PERMANENTS_GET))
                .forEach(ability -> {
                    if (ability.getMagicInstanceSelector() == null) {
                        throw new RuntimeException("Card '" + name + "' is missing magicInstanceSelector");
                    }

                    validateParameters(name, ability.getParameters());
                });
    }
  }

  private void validateParameters(String name, List<String> parameters) {
    if (parameters.isEmpty()) {
      throw new RuntimeException("Card '" + name + "' is missing parameters");
    }

    try {
      parameters.forEach(parameter -> abilityService.parameterAsString(parameter));
    } catch (Exception e) {
      throw new RuntimeException("Card '" + name + "' has invalid parameters: " + parameters, e);
    }
  }
}
