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
  private final TreeSet<Type> types;
  private final TreeSet<Subtype> subtypes;
  private final Integer power;
  private final Integer toughness;
  private final Rarity rarity;
  private final String oracleText;
  private final TreeSet<Color> colors;
  private final List<Cost> manaCost;

  @SneakyThrows
  public CardScryFallLinker(Card card) {
    try {
      String file = readHttpResource("https://api.scryfall.com/cards/named?fuzzy=" + URLEncoder.encode(card.getName(), "UTF-8"));
      JsonNode jsonNode = new ObjectMapper().readTree(file);
      image = jsonNode.path("image_uris").path("large").asText();
      String[] scryFallTypesSplit = jsonNode.path("type_line").asText().split(" " + SPECIAL_DASH + " ");
      types = convertType(scryFallTypesSplit);
      subtypes = convertSubtype(scryFallTypesSplit);
      power = intOrZero(jsonNode, "power");
      toughness = intOrZero(jsonNode, "toughness");
      rarity = Rarity.valueOf(jsonNode.path("rarity").asText().toUpperCase());
      oracleText = convertOracleText(jsonNode.path("oracle_text").asText());
      colors = convertColors(jsonNode.path("colors"));
      manaCost = convertCost(jsonNode.path("mana_cost").asText());

    } catch (Exception e) {
      System.err.println("Error loading card: " + card.getName());
      throw e;
    }
  }

  @SneakyThrows
  private String readHttpResource(String url) {
    return new BufferedReader(new InputStreamReader(new URL(url).openStream()))
      .lines().collect(Collectors.joining("\n"));
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

  private int intOrZero(JsonNode jsonNode, String power) {
    return jsonNode.has(power) ? Integer.parseInt(jsonNode.path(power).asText()) : 0;
  }

  private String convertOracleText(String oracleText) {
    oracleText = oracleText.replaceAll("\n", ". ");
    oracleText = oracleText.replaceAll("\\([^(]+\\)", "");
    oracleText = oracleText.replaceAll(" [ ]+", " ");
    oracleText = oracleText.replaceAll(" [.]+", ".");
    oracleText = oracleText.replaceAll("\\.[.]+", ".");
    return oracleText.trim();
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
}
