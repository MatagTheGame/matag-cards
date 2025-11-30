package com.matag.cards.ability.target

import com.matag.cards.ability.selector.MagicInstanceSelector

data class Target(
    var magicInstanceSelector: MagicInstanceSelector? = null,
    var optional: Boolean = false,
    var other: Boolean = false
)
