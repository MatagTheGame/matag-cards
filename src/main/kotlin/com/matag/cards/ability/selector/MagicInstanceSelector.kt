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
    var selectorType: SelectorType? = null,
    var ofType: MutableList<Type?>? = null,
    var ofAllTypes: MutableList<Type?>? = null,
    var notOfType: MutableList<Type?>? = null,
    var ofSubtype: MutableList<Subtype?>? = null,
    var notOfSubtype: MutableList<Subtype?>? = null,
    var withAbilityType: AbilityType? = null,
    var withoutAbilityType: AbilityType? = null,
    var ofColors: MutableList<Color?>? = null,
    var colorless: Boolean = false,
    var multicolor: Boolean = false,
    var powerToughnessConstraint: PowerToughnessConstraint? = null,
    var controllerType: PlayerType? = null,
    var statusTypes: MutableList<StatusType?>? = null,
    var others: Boolean = false,
    var itself: Boolean = false,
    var nonToken: Boolean = false,
    var currentEnchanted: Boolean = false,
    var turnStatusType: TurnStatusType? = null,
    var historic: Boolean = false
)
{
    @get:JsonIgnore
    val text: String
        get() {
            val stringBuilder = StringBuilder()

            if (selectorType == SelectorType.PERMANENT) {
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
                                .map<String?> { o: Subtype? -> Objects.toString(o) }
                                .map<String?> { obj: String? -> plural(obj!!) }
                                .map<String?> { word: String? -> this.spaced(word) }
                                .collect(Collectors.joining(", ")))
                    } else if (ofType != null) {
                        stringBuilder.append(
                            ofType!!.stream()
                                .map<String?> { o: Type? ->
                                    Objects.toString(
                                        o
                                    )
                                }
                                .map<String?> { obj: String? -> plural(obj!!) }
                                .map<String?> { word: String? -> this.spaced(word) }
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
            } else if (selectorType == SelectorType.PLAYER) {
                if (itself) {
                    return "You"
                } else {
                    if (controllerType == null || controllerType == PlayerType.PLAYER) {
                        return "Each player"
                    } else {
                        return "Each opponent"
                    }
                }
            }

            var str = stringBuilder.toString()
            str = str.lowercase(Locale.getDefault())
            str = if (str.isEmpty()) str else str.substring(0, 1).uppercase(Locale.getDefault()) + str.substring(1)

            return str.trim { it <= ' ' }
        }

    private fun spaced(word: String?): String {
        return word + " "
    }
}
