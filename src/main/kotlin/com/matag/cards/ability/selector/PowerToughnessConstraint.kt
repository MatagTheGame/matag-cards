package com.matag.cards.ability.selector

data class PowerToughnessConstraint(
    val powerOrToughness: PowerOrToughness,
    val powerToughnessConstraintType: PowerToughnessConstraintType,
    val value: Int = 0
) {
    enum class PowerOrToughness {
        POWER, TOUGHNESS
    }
}
