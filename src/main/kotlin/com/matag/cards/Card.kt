package com.matag.cards

import com.matag.cards.ability.Ability
import com.matag.cards.properties.*
import java.util.*

data class Card(
    val name: String,
    val imageUrl: String? = null,
    val colors: TreeSet<Color>? = null,
    val cost: List<Cost>? = null,
    val types: TreeSet<Type>? = null,
    val subtypes: TreeSet<Subtype>? = null,
    val rarity: Rarity? = null,
    val ruleText: String? = null,
    val power: Int? = null,
    val toughness: Int? = null,
    val abilities: List<Ability>? = null
) {
    fun colors(): Set<Color> {
        return colors.orEmpty()
    }
}
