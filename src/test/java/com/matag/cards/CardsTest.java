package com.matag.cards;

import com.matag.cards.ability.Ability;
import com.matag.cards.ability.selector.CardInstanceSelector;
import com.matag.cards.ability.selector.SelectorType;
import com.matag.cards.ability.target.Target;
import com.matag.cards.ability.trigger.Trigger;
import com.matag.cards.properties.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static com.matag.cards.ability.type.AbilityType.THAT_TARGETS_GET;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Import(CardsConfiguration.class)
public class CardsTest {

  @Autowired
  private Cards cards;

  @Test
  public void shouldLoadAllCards() {
    assertThat(cards.getAll()).isNotEmpty();

    for (Card card : cards.getAll()) {
      validateCard(card);
    }
  }

  @Test
  public void shouldLoadACardWithoutAbilities() {
    Card card = cards.get("Feral Maaka");
    assertThat(card.getName()).isEqualTo("Feral Maaka");
    Assertions.assertThat(card.getColors()).containsExactly(Color.RED);
    assertThat(card.getCost()).containsExactly(Cost.RED, Cost.COLORLESS);
    assertThat(card.getTypes()).containsExactly(Type.CREATURE);
    Assertions.assertThat(card.getSubtypes()).containsExactlyInAnyOrder(Subtype.CAT);
    Assertions.assertThat(card.getRarity()).isEqualTo(Rarity.COMMON);
    assertThat(card.getRuleText()).isNullOrEmpty();
    assertThat(card.getPower()).isEqualTo(2);
    assertThat(card.getToughness()).isEqualTo(2);
  }

  @Test
  public void shouldLoadACardWithAbilities() {
    Card card = cards.get("Act of Treason");
    assertThat(card.getName()).isEqualTo("Act of Treason");
    Assertions.assertThat(card.getColors()).containsExactly(Color.RED);
    assertThat(card.getCost()).containsExactly(Cost.RED, Cost.COLORLESS, Cost.COLORLESS);
    assertThat(card.getTypes()).containsExactly(Type.SORCERY);
    Assertions.assertThat(card.getSubtypes()).isNullOrEmpty();
    Assertions.assertThat(card.getRarity()).isEqualTo(Rarity.COMMON);
    assertThat(card.getRuleText()).isEqualTo("Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.");
    assertThat(card.getPower()).isEqualTo(0);
    assertThat(card.getToughness()).isEqualTo(0);
    assertThat(card.getAbilities()).hasSize(1);
    assertThat(card.getAbilities().get(0)).isEqualTo(
      Ability.builder()
        .abilityType(THAT_TARGETS_GET)
        .targets(singletonList(Target.builder()
          .cardInstanceSelector(CardInstanceSelector.builder()
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
    String name = card.getName();
    if (card.getImageUrl() == null) {
      throw new RuntimeException("Card '" + name + "' does not have an imageUrl. Run cardImageLinker");
    }

    if (card.getTypes().isEmpty()) {
      throw new RuntimeException("Card '" + name + "' does not have a type");
    }

    if (card.getRarity() == null) {
      throw new RuntimeException("Card '" + name + "' does not have rarity");
    }

    card.getAbilities().stream()
        .filter(ability -> ability.getAbilityType().equals(THAT_TARGETS_GET))
        .forEach(ability -> {
          if (ability.getTargets().isEmpty()) {
            throw new RuntimeException("Card '" + name + "' is missing targets");
          }
        });
  }
}
