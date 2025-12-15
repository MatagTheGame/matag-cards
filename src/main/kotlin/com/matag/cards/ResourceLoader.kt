package com.matag.cards

import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Component

@Component
class ResourceLoader {
    fun getCardsFileNames() = PathMatchingResourcePatternResolver(this.javaClass.classLoader)
            .getResources("/cards/*")

    fun getSetsFileNames() = PathMatchingResourcePatternResolver(this.javaClass.classLoader)
            .getResources("/sets/*")
}
