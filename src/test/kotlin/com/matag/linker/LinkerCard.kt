package com.matag.linker

import com.matag.cards.properties.*
import java.util.*

data class LinkerCard(
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
    val abilities: List<Object>? = null
)