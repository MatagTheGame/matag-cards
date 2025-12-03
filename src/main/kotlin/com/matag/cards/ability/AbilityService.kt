package com.matag.cards.ability

import com.matag.cards.ability.type.AbilityType
import com.matag.cards.properties.PowerToughness
import com.matag.language.replaceLast
import org.springframework.stereotype.Component
import java.util.*
import kotlin.math.abs

@Component
class AbilityService {

    fun powerToughnessFromParameters(parameters: List<String>) =
        parameters
            .filter { it.contains("/") }
            .map { it.split("/") }
            .map { PowerToughness(it[0].toInt(), it[1].toInt()) }
            .firstOrNull() ?: PowerToughness(0, 0)

    fun powerToughnessFromParameter(parameter: String) =
        powerToughnessFromParameters(listOf(parameter))

    fun damageFromParameter(parameter: String) =
        getParameterIntValue(parameter, DAMAGE)

    fun controllerDamageFromParameter(parameter: String) =
        getParameterIntValue(parameter, CONTROLLER_DAMAGE)

    fun tappedFromParameter(parameter: String) =
        parameter == TAPPED

    fun untappedFromParameter(parameter: String) =
        parameter == UNTAPPED

    fun doesNotUntapNextTurnFromParameter(parameter: String) =
        parameter == DOES_NOT_UNTAP_NEXT_TURN

    fun destroyedFromParameter(parameter: String) =
        parameter == DESTROYED

    fun returnToOwnerHandFromParameter(parameter: String) =
        parameter == RETURN_TO_OWNER_HAND

    fun controlledFromParameter(parameter: String) =
        parameter == CONTROLLED

    fun cancelledFromParameter(parameter: String) =
        parameter == CANCELLED

    fun plus1CountersFromParameter(parameter: String) =
        getParameterIntValue(parameter, PLUS_1_COUNTERS)

    fun minus1CountersFromParameter(parameter: String) =
        getParameterIntValue(parameter, MINUS_1_COUNTERS)

    fun keywordCounterFromParameter(parameter: String) =
        getParameterValue(parameter, KEYWORD_COUNTER)?.let { AbilityType.valueOf(it) }

    fun drawFromParameter(parameter: String) =
        getParameterIntValue(parameter, DRAW)

    fun lifeFromParameter(parameter: String) =
        getParameterIntValue(parameter, LIFE)

    fun scryFromParameter(parameter: String) =
        getParameterIntValue(parameter, SCRY)

    fun parametersAsString(parameters: List<String>) =
        parameters.joinToString(", ") { parameterAsString(it) }
            .replaceLast(",", " and")

    private fun parameterAsString(parameter: String): String {
        if (parameter.contains("/")) {
            return parameter
        } else if (parameter.startsWith("DAMAGE:")) {
            return damageFromParameter(parameter).toString() + " damage"
        } else if (parameter.startsWith("CONTROLLER_DAMAGE:")) {
            return "to its controller " + controllerDamageFromParameter(parameter) + " damage"
        } else if (tappedFromParameter(parameter)) {
            return "tapped"
        } else if (untappedFromParameter(parameter)) {
            return "untapped"
        } else if (doesNotUntapNextTurnFromParameter(parameter)) {
            return "doesn't untap next turn"
        } else if (destroyedFromParameter(parameter)) {
            return "destroyed"
        } else if (returnToOwnerHandFromParameter(parameter)) {
            return "returned to its owner's hand"
        } else if (controlledFromParameter(parameter)) {
            return "controlled"
        } else if (cancelledFromParameter(parameter)) {
            return "cancelled"
        } else if (parameter.startsWith("PLUS_1_COUNTERS:")) {
            return plus1CountersFromParameter(parameter).toString() + " +1/+1 counters"
        } else if (parameter.startsWith("MINUS_1_COUNTERS:")) {
            return minus1CountersFromParameter(parameter).toString() + " -1/-1 counters"
        } else if (parameter.startsWith("KEYWORD_COUNTER:")) {
            return "a " + abilityParameterAsString(keywordCounterFromParameter(parameter)!!) + " counter"
        } else if (parameter.startsWith("LIFE:")) {
            val life = lifeFromParameter(parameter)
            return (if (life > 0) "gain" else "lose") + " ${abs(life)} life"
        } else if (parameter.startsWith("DRAW:")) {
            val draw = drawFromParameter(parameter)
            return "draw " + draw + " card" + (if (draw > 1) "s" else "")
        } else if (parameter.startsWith("SCRY:")) {
            return "scry ${scryFromParameter(parameter)}"
        } else {
            return abilityParameterAsString(AbilityType.valueOf(parameter))
        }
    }

    private fun abilityParameterAsString(abilityType: AbilityType) =
        abilityType.text.dropLast(1).lowercase(Locale.getDefault())

    private fun getParameterValue(parameter: String, parameterType: String) =
        if (parameter.startsWith(parameterType)) {
            parameter.replace(parameterType, "")
        } else {
            null
        }

    private fun getParameterIntValue(parameter: String, parameterType: String) =
        getParameterValue(parameter, parameterType)?.toInt() ?: 0

    companion object {
        private const val DAMAGE = "DAMAGE:"
        private const val CONTROLLER_DAMAGE = "CONTROLLER_DAMAGE:"
        private const val TAPPED = ":TAPPED"
        private const val UNTAPPED = ":UNTAPPED"
        private const val DOES_NOT_UNTAP_NEXT_TURN = ":DOES_NOT_UNTAP_NEXT_TURN"
        private const val DESTROYED = ":DESTROYED"
        private const val RETURN_TO_OWNER_HAND = ":RETURN_TO_OWNER_HAND"
        private const val CONTROLLED = ":CONTROLLED"
        private const val CANCELLED = ":CANCELLED"
        private const val PLUS_1_COUNTERS = "PLUS_1_COUNTERS:"
        private const val MINUS_1_COUNTERS = "MINUS_1_COUNTERS:"
        private const val KEYWORD_COUNTER = "KEYWORD_COUNTER:"
        private const val DRAW = "DRAW:"
        private const val LIFE = "LIFE:"
        private const val SCRY = "SCRY:"
    }
}
