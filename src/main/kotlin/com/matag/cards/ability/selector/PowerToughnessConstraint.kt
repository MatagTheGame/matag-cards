package com.matag.cards.ability.selector

class PowerToughnessConstraint {
    enum class PowerOrToughness {
        POWER, TOUGHNESS
    }

    var powerOrToughness: PowerOrToughness? = null
    var powerToughnessConstraintType: PowerToughnessConstraintType? = null
    var value: Int = 0
}
