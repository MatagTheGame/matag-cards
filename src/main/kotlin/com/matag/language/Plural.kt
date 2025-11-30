package com.matag.language

import com.matag.language.StringUtils.replaceLast
import java.util.Map

object Plural {
    private val IRREGULARS: MutableMap<String?, String?> = Map.of<String?, String?>(
        "CYCLOPS", "CYCLOPES"
    )

    @JvmStatic
    fun plural(word: String): String {
        if (IRREGULARS.containsKey(word)) {
            return IRREGULARS.get(word)!!
        }
        if (word.endsWith("S") || word.endsWith("SH") || word.endsWith("CH") || word.endsWith("X") || word.endsWith("Z")) {
            return word + "ES"
        } else if (word.endsWith("F")) {
            return replaceLast(word, "F", "VES")
        } else {
            return word + "S"
        }
    }
}
