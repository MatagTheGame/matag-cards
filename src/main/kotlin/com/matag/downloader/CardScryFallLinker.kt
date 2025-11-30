package com.matag.downloader

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.matag.cards.Card
import com.matag.cards.properties.Color
import com.matag.cards.properties.Cost
import com.matag.cards.properties.Rarity
import com.matag.cards.properties.Subtype
import com.matag.cards.properties.Type
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.Map
import java.util.function.Supplier
import java.util.stream.Collectors
import java.util.stream.Stream

class CardScryFallLinker constructor(card: Card) {
    val image: String
    val colors: TreeSet<Color>?
    val manaCost: MutableList<Cost>?
    val types: TreeSet<Type>?
    val subtypes: TreeSet<Subtype>?
    val rarity: Rarity
    val oracleText: String
    val power: Int
    val toughness: Int
    val sets: MutableList<String>

    init {
        try {
            val file = readHttpResource(
                "https://api.scryfall.com/cards/search?order=released&q=" + URLEncoder.encode(
                    "!\"" + card.name + "\"",
                    StandardCharsets.UTF_8
                ) + "&unique=prints"
            )
            val jsonNode = ObjectMapper().readTree(file)
            checkSearchWorked(jsonNode)

            sets = convertSets(jsonNode)

            val cardJsonNode = findBestCardRepresentation(jsonNode)
            image = convertImageUrl(cardJsonNode)
            colors = convertColors(cardJsonNode.path("colors"))
            manaCost = convertCost(cardJsonNode.path("mana_cost").asText())
            val scryFallTypesSplit: Array<String> =
                cardJsonNode.path("type_line").asText().split((" " + SPECIAL_DASH + " ").toRegex())
                    .dropLastWhile { it.isEmpty() }.toTypedArray()
            types = convertType(scryFallTypesSplit)
            subtypes = convertSubtype(scryFallTypesSplit)
            rarity = Rarity.valueOf(cardJsonNode.path("rarity").asText().uppercase(Locale.getDefault()))
            oracleText = convertOracleText(cardJsonNode.path("oracle_text").asText())
            power = intOrZero(cardJsonNode, "power")
            toughness = intOrZero(cardJsonNode, "toughness")
        } catch (e: Exception) {
            throw Exception("Error loading card: " + card.name, e)
        }
    }

    private fun convertImageUrl(cardJsonNode: JsonNode): String {
        val fullUrl = cardJsonNode.path("image_uris").path("large").asText()
        return fullUrl.substring(0, fullUrl.indexOf("?"))
    }

    private fun findBestCardRepresentation(jsonNode: JsonNode): JsonNode {
        for (i in 0..<jsonNode.path("data").size()) {
            if (jsonNode.path("data").get(i).path("promo").asText().equals("false", ignoreCase = true)) {
                return jsonNode.path("data").get(i)
            }
        }
        return jsonNode.path("data").get(0)
    }

    private fun readHttpResource(url: String): String {
        return BufferedReader(InputStreamReader(URL(url).openStream()))
            .lines().collect(Collectors.joining("\n"))
    }

    @Throws(Exception::class)
    private fun checkSearchWorked(jsonNode: JsonNode) {
        if (intOrZero(jsonNode, "total_cards") == 0) {
            throw Exception("Card not found")
        }
    }

    private fun convertSets(jsonNode: JsonNode): MutableList<String> {
        val sets = ArrayList<String>()

        for (i in 0..<jsonNode.path("data").size()) {
            sets.add(jsonNode.path("data").get(i).path("set").asText().uppercase(Locale.getDefault()))
        }

        return sets
    }

    private fun convertColors(jsonColors: JsonNode): TreeSet<Color> {
        val colors = TreeSet<Color>()
        for (i in 0..<jsonColors.size()) {
            val color = jsonColors.get(i).asText()
            if (!COLORS.containsKey(color)) {
                throw RuntimeException("Color " + color + " not recognised")
            }
            colors.add(COLORS.get(color)!!)
        }
        return colors
    }

    private fun convertCost(manaCost: String): MutableList<Cost> {
        var manaCost = manaCost
        val cost = ArrayList<Cost>()

        for (costEntry in COSTS.entries) {
            while (manaCost.contains("{" + costEntry.key + "}")) {
                manaCost = manaCost.replaceFirst(("\\{" + costEntry.key + "}").toRegex(), "")
                cost.add(costEntry.value)
            }
        }

        manaCost = manaCost.replaceFirst("\\{".toRegex(), "").replaceFirst("}".toRegex(), "")
        if (!manaCost.isBlank()) {
            for (i in 0..<manaCost.toInt()) {
                cost.add(Cost.ANY)
            }
        }

        return cost
    }

    private fun convertType(scryFallTypesSplit: Array<String>): TreeSet<Type> {
        return Stream.of<String>(*scryFallTypesSplit[0]!!.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
            .map<String?> { obj: String? -> obj!!.uppercase(Locale.getDefault()) }
            .map<Type> { Type.valueOf(it) }
            .collect(Collectors.toCollection(Supplier { TreeSet() }))
    }

    private fun convertSubtype(scryFallTypesSplit: Array<String>): TreeSet<Subtype>? {
        if (scryFallTypesSplit.size < 2) {
            return null
        }

        return Stream.of<String>(*scryFallTypesSplit[1]!!.split(" ".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray())
            .map<String?> { obj: String? -> obj!!.uppercase(Locale.getDefault()) }
            .map<Subtype> { Subtype.valueOf(it) }
            .collect(Collectors.toCollection(Supplier { TreeSet() }))
    }

    private fun convertOracleText(oracleText: String): String {
        var oracleText = oracleText
        oracleText = oracleText.replace("\n".toRegex(), ". ")
        oracleText = oracleText.replace("\\([^(]+\\)".toRegex(), "")
        oracleText = oracleText.replace(" [ ]+".toRegex(), " ")
        oracleText = oracleText.replace(" [.]+".toRegex(), ".")
        oracleText = oracleText.replace("\\.[.]+".toRegex(), ".")
        return oracleText.trim { it <= ' ' }
    }

    private fun intOrZero(jsonNode: JsonNode, field: String?): Int {
        return if (jsonNode.has(field)) jsonNode.path(field).asText().toInt() else 0
    }

    companion object {
        private const val SPECIAL_DASH = "—" // it's not an ascii dash
        private val COLORS: MutableMap<String, Color> = Map.of<String, Color>(
            "W", Color.WHITE,
            "U", Color.BLUE,
            "B", Color.BLACK,
            "R", Color.RED,
            "G", Color.GREEN
        )
        private val COSTS = LinkedHashMap<String, Cost>()

        init {
            COSTS.put("C", Cost.COLORLESS)
            COSTS.put("W", Cost.WHITE)
            COSTS.put("U", Cost.BLUE)
            COSTS.put("B", Cost.BLACK)
            COSTS.put("R", Cost.RED)
            COSTS.put("G", Cost.GREEN)
        }
    }
}
