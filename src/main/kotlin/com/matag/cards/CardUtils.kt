package com.matag.cards

import com.matag.cards.properties.Color
import com.matag.cards.properties.Subtype
import com.matag.cards.properties.Type

fun Card.isOfType(type: Type) =
    this.types?.contains(type) ?: false

fun Card.isNotOfType(type: Type) =
    !this.isOfType(type)

fun Card.isOfSubtype(subtype: Subtype) =
    this.subtypes?.contains(subtype) ?: false

fun Card.isNotOfSubtype(subtype: Subtype) =
    !this.isOfSubtype(subtype)

fun Card.isColorless(): Boolean {
    return this.colors().isEmpty()
}

fun Card.isMulticolor(): Boolean {
    return this.colors().size > 1
}

fun Card.isOfColor(color: Color?): Boolean {
    return this.colors().contains(color)
}

fun Card.isOfOnlyAnyOfTheColors(colors: Set<Color>): Boolean {
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
