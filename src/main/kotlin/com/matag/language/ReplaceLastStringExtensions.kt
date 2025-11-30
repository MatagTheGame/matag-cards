package com.matag.language

fun String.replaceLast(regex: String?, replacement: String): String {
    return this.replaceFirst(("(?s)$regex(?!.*?$regex)").toRegex(), replacement)
}
