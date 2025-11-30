package com.matag.cards.ability.selector

import com.fasterxml.jackson.annotation.JsonIgnore
import com.matag.cards.ability.type.AbilityType
import com.matag.cards.properties.Color
import com.matag.cards.properties.Subtype
import com.matag.cards.properties.Type
import com.matag.language.Plural.plural
import com.matag.player.PlayerType
import java.util.*
import java.util.stream.Collectors

data class MagicInstanceSelector(
    var selectorType: SelectorType,
    var ofType: List<Type?>? = null,
    var ofAllTypes: List<Type?>? = null,
    var notOfType: List<Type?>? = null,
    var ofSubtype: List<Subtype?>? = null,
    var notOfSubtype: List<Subtype?>? = null,
    var withAbilityType: AbilityType? = null,
    var withoutAbilityType: AbilityType? = null,
    var ofColors: List<Color?>? = null,
    var colorless: Boolean = false,
    var multicolor: Boolean = false,
    var powerToughnessConstraint: PowerToughnessConstraint? = null,
    var controllerType: PlayerType? = null,
    var statusTypes: List<StatusType?>? = null,
    var others: Boolean = false,
    var itself: Boolean = false,
    var nonToken: Boolean = false,
    var currentEnchanted: Boolean = false,
    var turnStatusType: TurnStatusType? = null,
    var historic: Boolean = false
) {
    @JsonIgnore
    fun text() =
        when (selectorType!!) {
            SelectorType.PERMANENT -> permanentText()
            SelectorType.PLAYER -> playerText()
            SelectorType.ANY -> throw Exception("SelectorType.ANY not implemented!")
            SelectorType.CARD -> throw Exception("SelectorType.CARD not implemented!")
            SelectorType.SPELL -> throw Exception("SelectorType.SPELL not implemented!")
        }

    private fun permanentText(): String {
        val stringBuilder = StringBuilder()

        if (itself) {
            stringBuilder.append("gets")
        } else {
            if (others) {
                stringBuilder.append("other ")
            }

            if (currentEnchanted) {
                stringBuilder.append("enchanted ")
            }

            if (ofSubtype != null) {
                stringBuilder.append(
                    ofSubtype!!.stream()
                        .map { o: Subtype? -> Objects.toString(o) }
                        .map<String?> { obj: String? -> plural(obj!!) }
                        .map { word: String? -> this.spaced(word) }
                        .collect(Collectors.joining(", ")))

            } else if (ofType != null) {
                stringBuilder.append(
                    ofType!!.stream()
                        .map { o: Type? ->
                            Objects.toString(
                                o
                            )
                        }
                        .map<String?> { obj: String? -> plural(obj!!) }
                        .map { word: String? -> this.spaced(word) }
                        .collect(Collectors.joining(", ")))
            }

            if (controllerType != null) {
                if (controllerType == PlayerType.PLAYER) {
                    stringBuilder.append("you control ")
                } else {
                    stringBuilder.append("opponent controls ")
                }
            }

            stringBuilder.append("get")
        }

        var str = stringBuilder.toString()
        str = str.lowercase(Locale.getDefault())
        str = if (str.isEmpty()) str else str.take(1).uppercase(Locale.getDefault()) + str.substring(1)

        return str.trim { it <= ' ' }
    }

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

    private fun spaced(word: String?): String {
        return "$word "
    }
}
