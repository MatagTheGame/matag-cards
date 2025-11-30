package com.matag.cards.properties

data class PowerToughness(
    val power: Int,
    val toughness: Int
) {
    companion object {
        fun powerToughness(powerToughnessString: String): PowerToughness {
            val powerToughness: Array<String?> =
                powerToughnessString.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return PowerToughness(powerToughness[0]!!.toInt(), powerToughness[1]!!.toInt())
        }
    }
}
