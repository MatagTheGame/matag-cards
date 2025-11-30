package com.matag.language

object StringUtils {
    @JvmStatic
    fun replaceLast(text: String, regex: String?, replacement: String): String {
        return text.replaceFirst(("(?s)$regex(?!.*?$regex)").toRegex(), replacement)
    }
}
