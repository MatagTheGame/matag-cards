package com.matag.cards;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Cards {
  private final Map<String, Card> CARDS = new LinkedHashMap<>();

  public Cards(ObjectMapper objectMapper, ResourceLoader resourceLoader) {
    var cardResources = resourceLoader.getCardsFileNames();
    for (Resource cardResource : cardResources) {
      try {
        var card = objectMapper.readValue(cardResource.getInputStream(), Card.class);
        CARDS.put(card.getName(), card);

      } catch (Exception e) {
        throw new RuntimeException("Failed to load card: " + cardResource.getDescription(), e);
      }
    }
  }

  public List<Card> getAll() {
    return new ArrayList<>(CARDS.values());
  }

  public Map<String, Card> getCardsMap() {
    return CARDS;
  }

  public Card get(String name) {
    return Optional.ofNullable(CARDS.get(name))
        .orElseThrow(() -> new RuntimeException("Card " + name + " not found!"));
  }
}
