package com.matag.cards.sets

import com.fasterxml.jackson.databind.ObjectMapper
import com.matag.cards.ResourceLoader
import org.springframework.stereotype.Component

@Component
class MtgSets(objectMapper: ObjectMapper, resourceLoader: ResourceLoader) {
    val sets: MutableMap<String?, MtgSet?> = LinkedHashMap<String?, MtgSet?>()

    init {
        val setResources = resourceLoader.getSetsFileNames()
        for (setResource in setResources) {
            try {
                val mtgSet = objectMapper.readValue<MtgSet>(setResource.getInputStream(), MtgSet::class.java)
                sets.put(mtgSet.code, mtgSet)
            } catch (e: Exception) {
                throw RuntimeException("Failed to load set: " + setResource.getDescription(), e)
            }
        }
    }

    fun getSet(code: String?): MtgSet? {
        return sets.get(code)
    }
}
