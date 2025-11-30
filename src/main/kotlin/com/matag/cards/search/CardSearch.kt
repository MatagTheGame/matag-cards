package com.matag.cards.search

import com.matag.cards.Card
import com.matag.cards.CardUtils
import com.matag.cards.CardUtils.isColorless
import com.matag.cards.CardUtils.isMulticolor
import com.matag.cards.properties.Color
import com.matag.cards.properties.Subtype
import com.matag.cards.properties.Type
import java.util.stream.Collectors
import java.util.stream.Stream

class CardSearch(val cards: MutableList<Card?>) {
    fun ofType(type: Type?): CardSearch {
        val cards = this.cards.stream()
            .filter { card: Card? -> CardUtils.isOfType(card!!, type) }
            .collect(Collectors.toList())
        return CardSearch(cards)
    }

    fun notOfType(type: Type?): CardSearch {
        val cards = this.cards.stream()
            .filter { card: Card? -> CardUtils.isNotOfType(card!!, type) }
            .collect(Collectors.toList())
        return CardSearch(cards)
    }

    fun ofSubtype(subtype: Subtype?): CardSearch {
        val cards = this.cards.stream()
            .filter { card: Card? -> CardUtils.isOfSubtype(card!!, subtype) }
            .collect(Collectors.toList())
        return CardSearch(cards)
    }

    fun notOfSubtype(subtype: Subtype?): CardSearch {
        val cards = this.cards.stream()
            .filter { card: Card? -> CardUtils.isNotOfSubtype(card!!, subtype) }
            .collect(Collectors.toList())
        return CardSearch(cards)
    }

    fun ofColor(color: Color?): CardSearch {
        val cards = this.cards.stream()
            .filter { card: Card? -> CardUtils.isOfColor(card!!, color) }
            .collect(Collectors.toList())
        return CardSearch(cards)
    }

    fun ofOnlyAnyOfTheColors(colors: MutableSet<Color?>): CardSearch {
        val cards = this.cards.stream()
            .filter { card: Card? -> CardUtils.isOfOnlyAnyOfTheColors(card!!, colors) }
            .collect(Collectors.toList())
        return CardSearch(cards)
    }

    fun colorless(): CardSearch {
        val cards = this.cards.stream()
            .filter { obj: Card? -> isColorless(obj!!) }
            .collect(Collectors.toList())
        return CardSearch(cards)
    }

    fun multicolor(): CardSearch {
        val cards = this.cards.stream()
            .filter { obj: Card? -> isMulticolor(obj!!) }
            .collect(Collectors.toList())
        return CardSearch(cards)
    }

    fun concat(moreCards: MutableList<Card?>): CardSearch {
        val cards = Stream.concat<Card?>(this.cards.stream(), moreCards.stream()).collect(Collectors.toList())
        return CardSearch(cards)
    }

    fun isEmpty() = cards.isEmpty()

    fun isNotEmpty() = !cards.isEmpty()
}
