package com.matag.cards.sets

import com.fasterxml.jackson.databind.ObjectMapper
import com.matag.cards.ResourceLoader
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class MtgSets(val objectMapper: ObjectMapper, resourceLoader: ResourceLoader) {
    val sets: Map<String, MtgSet> = resourceLoader.getSetsFileNames()
        .map { parseSet(it) }
        .associateBy { it.code }

    fun get(code: String) =
        sets[code] ?: throw Exception("Set $code not found!")

    private fun parseSet(resource: Resource): MtgSet =
        try {
            objectMapper.readValue(resource.inputStream, MtgSet::class.java)
        } catch (e: Exception) {
            throw Exception("Failed to load set: " + resource.description, e)
        }
}
