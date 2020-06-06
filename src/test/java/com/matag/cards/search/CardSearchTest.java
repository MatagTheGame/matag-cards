package com.matag.cards.search;

import com.matag.cards.Card;
import com.matag.cards.Cards;
import com.matag.cards.CardsConfiguration;
import com.matag.cards.properties.Color;
import com.matag.cards.properties.Type;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Import(CardsConfiguration.class)
public class CardSearchTest {
  @Autowired
  private Cards cards;

  @Test
  public void empty() {
    assertThat(new CardSearch(emptyList()).isEmpty()).isTrue();
  }

  @Test
  public void notEmpty() {
    assertThat(new CardSearch(List.of(cards.get("Plains"))).isNotEmpty()).isTrue();
  }

  @Test
  public void concat() {
    assertThat(new CardSearch(List.of(cards.get("Plains")))
        .concat(List.of(cards.get("Mountain")))
        .getCards())
        .hasSize(2);
  }

  @Test
  public void ofType() {
    // Given
    CardSearch cardSearch = new CardSearch(List.of(cards.get("Plains"), cards.get("Bedevil")));

    // When
    List<Card> result = cardSearch.ofType(Type.LAND).getCards();

    // Then
    assertThat(result).contains(cards.get("Plains"));
  }

  @Test
  public void notOfType() {
    // Given
    CardSearch cardSearch = new CardSearch(List.of(cards.get("Plains"), cards.get("Bedevil")));

    // When
    List<Card> result = cardSearch.notOfType(Type.LAND).getCards();

    // Then
    assertThat(result).contains(cards.get("Bedevil"));
  }

  @Test
  public void ofColor() {
    // Given
    CardSearch cardSearch = new CardSearch(List.of(
        cards.get("Empyrean Eagle"), // white blue
        cards.get("Angel of the Dawn"), // white
        cards.get("Dark Nourishment") // black
    ));

    // When
    List<Card> result = cardSearch.ofColor(Color.WHITE).getCards();

    // Then
    assertThat(result).contains(cards.get("Empyrean Eagle"), cards.get("Angel of the Dawn"));
  }

  @Test
  public void ofOnlyAnyOfTheColors() {
    // Given
    CardSearch cardSearch = new CardSearch(List.of(
        cards.get("Empyrean Eagle"), // white blue
        cards.get("Angel of the Dawn"), // white
        cards.get("Swiftblade Vindicator"), // white red
        cards.get("Dark Nourishment") // black
    ));

    // When
    List<Card> result = cardSearch.ofOnlyAnyOfTheColors(Set.of(Color.WHITE, Color.BLUE)).getCards();

    // Then
    assertThat(result).contains(cards.get("Empyrean Eagle"), cards.get("Angel of the Dawn"));
  }

  @Test
  public void colorless() {
    // Given
    CardSearch cardSearch = new CardSearch(List.of(
        cards.get("Empyrean Eagle"),
        cards.get("Jousting Dummy")
    ));

    // When
    List<Card> result = cardSearch.colorless().getCards();

    // Then
    assertThat(result).contains(cards.get("Jousting Dummy"));
  }
}