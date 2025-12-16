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
    fun isOfType(type: Type) =
        this.types.contains(type)

    fun isNotOfType(type: Type) =
        !isOfType(type)

    fun isOfSubtype(subtype: Subtype) =
        subtypes.contains(subtype)

    fun isNotOfSubtype(subtype: Subtype) =
        !isOfSubtype(subtype)

    fun isColorless() =
        colors.isEmpty()

    fun isMulticolor() =
        colors.size > 1

    fun isOfColor(color: Color) =
        colors.contains(color)

    fun isOfOnlyAnyOfTheColors(colors: Set<Color>): Boolean {
        if (this.colors.isEmpty()) {
            return false
        }

        for (color in this.colors) {
            if (!colors.contains(color)) {
                return false
            }
        }
        return true
    }
}
