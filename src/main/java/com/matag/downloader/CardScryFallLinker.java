package com.matag.downloader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matag.cards.Card;
import com.matag.cards.properties.*;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class CardScryFallLinker {
  private static final String SPECIAL_DASH = "—"; // it's not an ascii dash
  private static final Map<String, Color> COLORS = Map.of(
      "W", Color.WHITE,
      "U", Color.BLUE,
      "B", Color.BLACK,
      "R", Color.RED,
      "G", Color.GREEN
  );
  private static final LinkedHashMap<String, Cost> COSTS = new LinkedHashMap<>();
  static {
    COSTS.put("W", Cost.WHITE);
    COSTS.put("U", Cost.BLUE);
    COSTS.put("B", Cost.BLACK);
    COSTS.put("R", Cost.RED);
    COSTS.put("G", Cost.GREEN);
  }

  private final String image;
  private final TreeSet<Color> colors;
  private final List<Cost> manaCost;
  private final TreeSet<Type> types;
  private final TreeSet<Subtype> subtypes;
  private final Rarity rarity;
  private final String oracleText;
  private final Integer power;
  private final Integer toughness;
  private final List<String> sets;

  @SneakyThrows
  public CardScryFallLinker(Card card) {
    try {
      String file = readHttpResource("https://api.scryfall.com/cards/search?order=released&q=" + URLEncoder.encode("!\"" + card.getName() + "\"", "UTF-8") + "&unique=prints");
      JsonNode jsonNode = new ObjectMapper().readTree(file);
      checkSearchWorked(jsonNode);

      sets = convertSets(jsonNode);

      JsonNode cardJsonNode = findBestCardRepresentation(jsonNode);
      image = cardJsonNode.path("image_uris").path("large").asText();
      colors = convertColors(cardJsonNode.path("colors"));
      manaCost = convertCost(cardJsonNode.path("mana_cost").asText());
      String[] scryFallTypesSplit = cardJsonNode.path("type_line").asText().split(" " + SPECIAL_DASH + " ");
      types = convertType(scryFallTypesSplit);
      subtypes = convertSubtype(scryFallTypesSplit);
      rarity = Rarity.valueOf(cardJsonNode.path("rarity").asText().toUpperCase());
      oracleText = convertOracleText(cardJsonNode.path("oracle_text").asText());
      power = intOrZero(cardJsonNode, "power");
      toughness = intOrZero(cardJsonNode, "toughness");

    } catch (Exception e) {
      throw new Exception("Error loading card: " + card.getName(), e);
    }
  }

  private JsonNode findBestCardRepresentation(JsonNode jsonNode) {
    for (int i = 0; i < jsonNode.path("data").size(); i++) {
      if (jsonNode.path("data").get(i).path("promo").asText().equalsIgnoreCase("false")) {
        return jsonNode.path("data").get(i);
      }
    }
    return jsonNode.path("data").get(0);
  }

  @SneakyThrows
  private String readHttpResource(String url) {
    return new BufferedReader(new InputStreamReader(new URL(url).openStream()))
      .lines().collect(Collectors.joining("\n"));
  }

  private void checkSearchWorked(JsonNode jsonNode) throws Exception {
    if (intOrZero(jsonNode, "total_cards") == 0) {
      throw new Exception("Card not found");
    }
  }

  private List<String> convertSets(JsonNode jsonNode) {
    List<String> sets = new ArrayList<>();

    for (int i = 0; i < jsonNode.path("data").size(); i++) {
      sets.add(jsonNode.path("data").get(i).path("set").asText().toUpperCase());
    }
    
    return sets;
  }

  private TreeSet<Color> convertColors(JsonNode jsonColors) {
    TreeSet<Color> colors = new TreeSet<>();
    for (int i = 0; i < jsonColors.size(); i++) {
      String color = jsonColors.get(i).asText();
      if (!COLORS.containsKey(color)) {
        throw new RuntimeException("Color " + color + " not recognised");
      }
      colors.add(COLORS.get(color));
    }
    return colors;
  }

  private List<Cost> convertCost(String manaCost) {
    List<Cost> cost = new ArrayList<>();

    for (Map.Entry<String, Cost> costEntry : COSTS.entrySet()) {
      while (manaCost.contains("{" + costEntry.getKey() + "}")) {
        manaCost = manaCost.replaceFirst("\\{" + costEntry.getKey() + "}", "");
        cost.add(costEntry.getValue());
      }
    }

    manaCost = manaCost.replaceFirst("\\{", "").replaceFirst("}", "");
    if (!manaCost.isBlank()) {
      for (int i = 0; i < Integer.parseInt(manaCost); i++) {
        cost.add(Cost.COLORLESS);
      }
    }

    return cost;
  }

  private TreeSet<Type> convertType(String[] scryFallTypesSplit) {
    return Stream.of(scryFallTypesSplit[0].split(" "))
        .map(String::toUpperCase)
        .map(Type::valueOf)
        .collect(Collectors.toCollection(TreeSet::new));
  }

  private TreeSet<Subtype> convertSubtype(String[] scryFallTypesSplit) {
    if (scryFallTypesSplit.length < 2) {
      return null;
    }

    return Stream.of(scryFallTypesSplit[1].split(" "))
        .map(String::toUpperCase)
        .map(Subtype::valueOf)
        .collect(Collectors.toCollection(TreeSet::new));
  }

  private String convertOracleText(String oracleText) {
    oracleText = oracleText.replaceAll("\n", ". ");
    oracleText = oracleText.replaceAll("\\([^(]+\\)", "");
    oracleText = oracleText.replaceAll(" [ ]+", " ");
    oracleText = oracleText.replaceAll(" [.]+", ".");
    oracleText = oracleText.replaceAll("\\.[.]+", ".");
    return oracleText.trim();
  }

  private int intOrZero(JsonNode jsonNode, String field) {
    return jsonNode.has(field) ? Integer.parseInt(jsonNode.path(field).asText()) : 0;
  }
}
