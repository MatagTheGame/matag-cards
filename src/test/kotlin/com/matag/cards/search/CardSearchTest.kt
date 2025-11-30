package com.matag.cards.search;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import com.matag.cards.Cards;
import com.matag.cards.CardsConfiguration;
import com.matag.cards.properties.Color;
import com.matag.cards.properties.Subtype;
import com.matag.cards.properties.Type;

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
        var cardSearch = new CardSearch(List.of(cards.get("Plains"), cards.get("Bedevil")));

        // When
        var result = cardSearch.ofType(Type.LAND).getCards();

        // Then
        assertThat(result).contains(cards.get("Plains"));
    }

    @Test
    public void notOfType() {
        // Given
        var cardSearch = new CardSearch(List.of(cards.get("Plains"), cards.get("Bedevil")));

        // When
        var result = cardSearch.notOfType(Type.LAND).getCards();

        // Then
        assertThat(result).contains(cards.get("Bedevil"));
    }

    @Test
    public void ofSubtype() {
        // Given
        var cardSearch = new CardSearch(List.of(cards.get("Plains"), cards.get("Dusk Legion Zealot")));

        // When
        var result = cardSearch.ofSubtype(Subtype.SOLDIER).getCards();

        // Then
        assertThat(result).contains(cards.get("Dusk Legion Zealot"));
    }

    @Test
    public void notOfSubtype() {
        // Given
        var cardSearch = new CardSearch(List.of(cards.get("Plains"), cards.get("Dusk Legion Zealot")));

        // When
        var result = cardSearch.notOfSubtype(Subtype.SOLDIER).getCards();

        // Then
        assertThat(result).contains(cards.get("Plains"));
    }

    @Test
    public void ofColor() {
        // Given
        var cardSearch = new CardSearch(List.of(
                cards.get("Empyrean Eagle"), // white blue
                cards.get("Angel of the Dawn"), // white
                cards.get("Dark Nourishment") // black
        ));

        // When
        var result = cardSearch.ofColor(Color.WHITE).getCards();

        // Then
        assertThat(result).contains(cards.get("Empyrean Eagle"), cards.get("Angel of the Dawn"));
    }

    @Test
    public void ofOnlyAnyOfTheColors() {
        // Given
        var cardSearch = new CardSearch(List.of(
                cards.get("Empyrean Eagle"), // white blue
                cards.get("Angel of the Dawn"), // white
                cards.get("Swiftblade Vindicator"), // white red
                cards.get("Dark Nourishment") // black
        ));

        // When
        var result = cardSearch.ofOnlyAnyOfTheColors(Set.of(Color.WHITE, Color.BLUE)).getCards();

        // Then
        assertThat(result).contains(cards.get("Empyrean Eagle"), cards.get("Angel of the Dawn"));
    }

    @Test
    public void colorless() {
        // Given
        var cardSearch = new CardSearch(List.of(
                cards.get("Empyrean Eagle"), // white blue
                cards.get("Angel of the Dawn"), // white
                cards.get("Jousting Dummy") // colorless
        ));

        // When
        var result = cardSearch.colorless().getCards();

        // Then
        assertThat(result).contains(cards.get("Jousting Dummy"));
    }

    @Test
    public void multicolor() {
        // Given
        var cardSearch = new CardSearch(List.of(
                cards.get("Empyrean Eagle"), // white blue
                cards.get("Angel of the Dawn"), // white
                cards.get("Jousting Dummy") // colorless
        ));

        // When
        var result = cardSearch.multicolor().getCards();

        // Then
        assertThat(result).contains(cards.get("Empyrean Eagle"));
    }
}