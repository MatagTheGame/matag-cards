package com.matag.cards

import com.matag.cards.ability.Ability
import com.matag.cards.properties.*
import java.util.*

data class Card(
    val name: String,
    val imageUrl: String?,
    val colors: TreeSet<Color>?,
    val cost: List<Cost>?,
    val types: TreeSet<Type>?,
    val subtypes: TreeSet<Subtype>?,
    val rarity: Rarity?,
    val ruleText: String?,
    val power: Int?,
    val toughness: Int?,
    val abilities: MutableList<Ability>?
) {
    fun colors(): Set<Color> {
        return colors.orEmpty()
    }
}
