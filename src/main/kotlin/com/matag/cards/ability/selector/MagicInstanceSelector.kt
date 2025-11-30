package com.matag.cards.ability.selector

import com.fasterxml.jackson.annotation.JsonIgnore
import com.matag.cards.ability.type.AbilityType
import com.matag.cards.properties.Color
import com.matag.cards.properties.Subtype
import com.matag.cards.properties.Type
import com.matag.language.Plural.plural
import com.matag.player.PlayerType

data class MagicInstanceSelector(
    val selectorType: SelectorType,
    val ofType: List<Type>? = null,
    val ofAllTypes: List<Type>? = null,
    val notOfType: List<Type>? = null,
    val ofSubtype: List<Subtype>? = null,
    val notOfSubtype: List<Subtype>? = null,
    val withAbilityType: AbilityType? = null,
    val withoutAbilityType: AbilityType? = null,
    val ofColors: List<Color>? = null,
    val colorless: Boolean = false,
    val multicolor: Boolean = false,
    val powerToughnessConstraint: PowerToughnessConstraint? = null,
    val controllerType: PlayerType? = null,
    val statusTypes: List<StatusType>? = null,
    val others: Boolean = false,
    val itself: Boolean = false,
    val nonToken: Boolean = false,
    val currentEnchanted: Boolean = false,
    val turnStatusType: TurnStatusType? = null,
    val historic: Boolean = false
) {
    @JsonIgnore
    fun text() =
        when (selectorType) {
            SelectorType.PERMANENT -> permanentText()
            SelectorType.PLAYER -> playerText()
            SelectorType.ANY -> throw Exception("SelectorType.ANY not implemented!")
            SelectorType.CARD -> throw Exception("SelectorType.CARD not implemented!")
            SelectorType.SPELL -> throw Exception("SelectorType.SPELL not implemented!")
        }

    private fun permanentText(): String {
        if (itself) {
            return "Gets"

        } else {
            val stringBuilder = StringBuilder()

            if (others) {
                stringBuilder.append("other ")
            }

            if (currentEnchanted) {
                stringBuilder.append("enchanted ")
            }

            if (ofSubtype != null) {
                stringBuilder.append(allTypes(ofSubtype))

            } else if (ofType != null) {
                stringBuilder.append(allTypes(ofType))
            }

            if (controllerType != null) {
                if (controllerType == PlayerType.PLAYER) {
                    stringBuilder.append("you control ")
                } else {
                    stringBuilder.append("opponent controls ")
                }
            }

            stringBuilder.append("get")

            return stringBuilder.toString().replaceFirstChar { it.titlecase() }
        }
    }

    private fun allTypes(list: List<Any>): String =
        list
            .map { plural(it.toString()).lowercase() }
            .joinToString("and ") { "$it " }

    private fun playerText(): String {
        return if (itself) {
            "You"
        } else {
            if (controllerType == null || controllerType == PlayerType.PLAYER) {
                "Each player"
            } else {
                "Each opponent"
            }
        }
    }
}
