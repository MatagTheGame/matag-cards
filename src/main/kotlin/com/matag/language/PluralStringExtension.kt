package com.matag.language

private val IRREGULARS_WORDS = mapOf(
    "CYCLOPS" to "CYCLOPES"
)

private val ENDINGS_THAT_REQUIRES_APPENDING_ES = listOf("S", "SH", "CH", "X", "Z")

fun String.plural(): String {
    return IRREGULARS_WORDS.getOrElse(this) {
        regularPlural(this)
    }
}

private fun regularPlural(word: String): String {
    return if (ENDINGS_THAT_REQUIRES_APPENDING_ES.any { word.endsWith(it) }) {
        word + "ES"

    } else if (word.endsWith("F")) {
        word.replaceLast("F", "VES")

    } else {
        word + "S"
    }
}
