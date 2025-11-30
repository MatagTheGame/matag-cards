package com.matag.cards.search;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.matag.cards.Card;
import com.matag.cards.CardUtils;
import com.matag.cards.properties.Color;
import com.matag.cards.properties.Subtype;
import com.matag.cards.properties.Type;

public class CardSearch {

    private final List<Card> cards;

    public CardSearch(List<Card> cards) {
        this.cards = cards;
    }

    public CardSearch ofType(Type type) {
        var cards = this.cards.stream()
                .filter(card -> CardUtils.isOfType(card, type))
                .collect(toList());
        return new CardSearch(cards);
    }

    public CardSearch notOfType(Type type) {
        var cards = this.cards.stream()
                .filter(card -> CardUtils.isNotOfType(card, type))
                .collect(toList());
        return new CardSearch(cards);
    }

    public CardSearch ofSubtype(Subtype subtype) {
        var cards = this.cards.stream()
                .filter(card -> CardUtils.isOfSubtype(card, subtype))
                .collect(toList());
        return new CardSearch(cards);
    }

    public CardSearch notOfSubtype(Subtype subtype) {
        var cards = this.cards.stream()
                .filter(card -> CardUtils.isNotOfSubtype(card, subtype))
                .collect(toList());
        return new CardSearch(cards);
    }

    public CardSearch ofColor(Color color) {
        var cards = this.cards.stream()
                .filter(card -> CardUtils.isOfColor(card, color))
                .collect(toList());
        return new CardSearch(cards);
    }

    public CardSearch ofOnlyAnyOfTheColors(Set<Color> colors) {
        var cards = this.cards.stream()
                .filter(card -> CardUtils.isOfOnlyAnyOfTheColors(card, colors))
                .collect(toList());
        return new CardSearch(cards);
    }

    public CardSearch colorless() {
        var cards = this.cards.stream()
                .filter(CardUtils::isColorless)
                .collect(toList());
        return new CardSearch(cards);
    }

    public CardSearch multicolor() {
        var cards = this.cards.stream()
                .filter(CardUtils::isMulticolor)
                .collect(toList());
        return new CardSearch(cards);
    }

    public CardSearch concat(List<Card> moreCards) {
        var cards = Stream.concat(this.cards.stream(), moreCards.stream()).collect(toList());
        return new CardSearch(cards);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public boolean isNotEmpty() {
        return !cards.isEmpty();
    }

    public List<Card> getCards() {
        return cards;
    }
}
