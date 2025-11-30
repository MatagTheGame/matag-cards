package com.matag.cards

import com.matag.cards.properties.Color
import com.matag.cards.properties.Subtype
import com.matag.cards.properties.Type

object CardUtils {
    @JvmStatic
    fun isOfType(card: Card, type: Type?): Boolean {
        return card.types!!.contains(type!!)
    }

    @JvmStatic
    fun isNotOfType(card: Card, type: Type?): Boolean {
        return !isOfType(card, type)
    }

    @JvmStatic
    fun isOfSubtype(card: Card, subtype: Subtype?): Boolean {
        return card.subtypes!!.contains(subtype!!)
    }

    @JvmStatic
    fun isNotOfSubtype(card: Card, subtype: Subtype?): Boolean {
        return !isOfSubtype(card, subtype)
    }

    @JvmStatic
    fun isColorless(card: Card): Boolean {
        return card.colors().isEmpty()
    }

    @JvmStatic
    fun isMulticolor(card: Card): Boolean {
        return card.colors().size > 1
    }

    @JvmStatic
    fun isOfColor(card: Card, color: Color?): Boolean {
        return card.colors().contains(color)
    }

    @JvmStatic
    fun isOfOnlyAnyOfTheColors(card: Card, colors: MutableSet<Color?>): Boolean {
        if (card.colors().isEmpty()) {
            return false
        }

        for (color in card.colors()) {
            if (!colors.contains(color)) {
                return false
            }
        }
        return true
    }
}
