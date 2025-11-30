package com.matag.cards.ability

import com.matag.cards.ability.type.AbilityType
import com.matag.cards.properties.PowerToughness
import com.matag.language.replaceLast
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors

@Component
class AbilityService {
    fun powerToughnessFromParameters(parameters: MutableList<String?>): PowerToughness {
        return parameters.stream()
            .filter { parameter: String? -> parameter!!.contains("/") }
            .map<PowerToughness> { powerToughnessString: String? -> PowerToughness.powerToughness(powerToughnessString!!) }
            .findFirst()
            .orElse(PowerToughness.powerToughness("0/0"))
    }

    fun powerToughnessFromParameter(parameter: String?): PowerToughness {
        return powerToughnessFromParameters(mutableListOf<String?>(parameter))
    }

    fun damageFromParameter(parameter: String): Int {
        return getParameterIntValue(parameter, DAMAGE)
    }

    fun controllerDamageFromParameter(parameter: String): Int {
        return getParameterIntValue(parameter, CONTROLLER_DAMAGE)
    }

    fun tappedFromParameter(parameter: String): Boolean {
        return parameter == TAPPED
    }

    fun untappedFromParameter(parameter: String): Boolean {
        return parameter == UNTAPPED
    }

    fun doesNotUntapNextTurnFromParameter(parameter: String): Boolean {
        return parameter == DOES_NOT_UNTAP_NEXT_TURN
    }

    fun destroyedFromParameter(parameter: String): Boolean {
        return parameter == DESTROYED
    }

    fun returnToOwnerHandFromParameter(parameter: String): Boolean {
        return parameter == RETURN_TO_OWNER_HAND
    }

    fun controlledFromParameter(parameter: String): Boolean {
        return parameter == CONTROLLED
    }

    fun cancelledFromParameter(parameter: String): Boolean {
        return parameter == CANCELLED
    }

    fun plus1CountersFromParameter(parameter: String): Int {
        return getParameterIntValue(parameter, PLUS_1_COUNTERS)
    }

    fun minus1CountersFromParameter(parameter: String): Int {
        return getParameterIntValue(parameter, MINUS_1_COUNTERS)
    }

    fun keywordCounterFromParameter(parameter: String): AbilityType? {
        return AbilityType.abilityType(getParameterValue(parameter, KEYWORD_COUNTER))
    }

    fun drawFromParameter(parameter: String): Int {
        return getParameterIntValue(parameter, DRAW)
    }

    fun lifeFromParameter(parameter: String): Int {
        return getParameterIntValue(parameter, LIFE)
    }

    fun scryFromParameter(parameter: String): Int {
        return getParameterIntValue(parameter, SCRY)
    }

    fun parametersAsString(parameters: MutableList<String?>): String {
        val text = parameters.stream().map<String?> { parameter: String? -> this.safeParameterAsString(parameter!!) }
            .collect(Collectors.joining(", "))
        return text.replaceLast(",", " and")
    }

    fun safeParameterAsString(parameter: String): String {
        try {
            return parameterAsString(parameter)
        } catch (e: Exception) {
            return parameter.lowercase(Locale.getDefault())
        }
    }

    @Throws(RuntimeException::class)
    fun parameterAsString(parameter: String): String {
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
            return (if (life > 0) "gain " + life else "lose " + (-life)) + " life"
        } else if (parameter.startsWith("DRAW:")) {
            val draw = drawFromParameter(parameter)
            return "draw " + draw + " card" + (if (draw > 1) "s" else "")
        } else if (parameter.startsWith("SCRY:")) {
            val scry = scryFromParameter(parameter)
            return "scry " + scry
        } else {
            return abilityParameterAsString(AbilityType.valueOf(parameter))
        }
    }

    private fun abilityParameterAsString(abilityType: AbilityType): String {
        return abilityType.text.substring(0, abilityType.text.length - 1).lowercase(Locale.getDefault())
    }

    private fun getParameterValue(parameter: String, parameterType: String): String? {
        if (parameter.startsWith(parameterType)) {
            return parameter.replace(parameterType, "")
        }
        return null
    }

    private fun getParameterIntValue(parameter: String, parameterType: String): Int {
        return getParameterValue(parameter, parameterType)?.toInt() ?: 0
    }

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
