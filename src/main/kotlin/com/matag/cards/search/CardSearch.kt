package com.matag.cards.search

import com.matag.cards.*
import com.matag.cards.properties.Color
import com.matag.cards.properties.Subtype
import com.matag.cards.properties.Type

class CardSearch(val cards: List<Card>) {
    fun ofType(type: Type) =
        CardSearch(cards.filter { it.isOfType(type) })

    fun notOfType(type: Type) =
        CardSearch(cards.filter { it.isNotOfType(type) })

    fun ofSubtype(subtype: Subtype) =
        CardSearch(cards.filter { it.isOfSubtype(subtype) })

    fun notOfSubtype(subtype: Subtype) =
        CardSearch(cards.filter { it.isNotOfSubtype(subtype) })

    fun ofColor(color: Color) =
        CardSearch(cards.filter { it.isOfColor(color) })

    fun ofOnlyAnyOfTheColors(colors: Set<Color>) =
        CardSearch(cards.filter { it.isOfOnlyAnyOfTheColors(colors) })

    fun colorless() =
        CardSearch(cards.filter { it.isColorless() })

    fun multicolor() =
        CardSearch(cards.filter { it.isMulticolor() })

    fun concat(moreCards: List<Card>) =
        CardSearch(this.cards + moreCards)

    fun isEmpty() =
        cards.isEmpty()

    fun isNotEmpty() =
        cards.isNotEmpty()
}
