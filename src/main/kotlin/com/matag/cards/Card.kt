package com.matag.cards

import com.matag.cards.ability.Ability
import com.matag.cards.properties.*
import java.util.*

data class Card(
    val name: String,
    val imageUrl: String,
    val colors: TreeSet<Color> = TreeSet(),
    val cost: List<Cost> = listOf(),
    val types: TreeSet<Type>,
    val subtypes: TreeSet<Subtype> = TreeSet(),
    val rarity: Rarity,
    val ruleText: String = "",
    val power: Int? = null,
    val toughness: Int? = null,
    val abilities: List<Ability> = listOf()
) {
    fun colors() =
        colors.orEmpty()

    fun isOfType(type: Type) =
        this.types?.contains(type) ?: false

    fun isNotOfType(type: Type) =
        !this.isOfType(type)

    fun isOfSubtype(subtype: Subtype) =
        this.subtypes?.contains(subtype) ?: false

    fun isNotOfSubtype(subtype: Subtype) =
        !this.isOfSubtype(subtype)

    fun isColorless(): Boolean {
        return this.colors().isEmpty()
    }

    fun isMulticolor(): Boolean {
        return this.colors().size > 1
    }

    fun isOfColor(color: Color?): Boolean {
        return this.colors().contains(color)
    }

    fun isOfOnlyAnyOfTheColors(colors: Set<Color>): Boolean {
        if (this.colors().isEmpty()) {
            return false
        }

        for (color in this.colors()) {
            if (!colors.contains(color)) {
                return false
            }
        }
        return true
    }
}
