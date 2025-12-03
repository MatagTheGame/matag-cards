package com.matag.cards

import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Component

@Component
class ResourceLoader {
    fun getCardsFileNames() = PathMatchingResourcePatternResolver(this.javaClass.getClassLoader())
            .getResources("/cards/*")

    fun getSetsFileNames() = PathMatchingResourcePatternResolver(this.javaClass.getClassLoader())
            .getResources("/sets/*")
}
