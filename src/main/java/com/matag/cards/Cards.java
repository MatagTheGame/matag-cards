package com.matag.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class Cards {
  private Map<String, Card> CARDS = new LinkedHashMap<>();

  public Cards(ObjectMapper objectMapper, ResourceLoader resourceLoader) {
    String[] cardResources = resourceLoader.getCardsFileNames();
    for (String cardResource : cardResources) {
      try {
        String cardJson = resourceLoader.getCardJson(cardResource);
        Card card = objectMapper.readValue(cardJson, Card.class);
        CARDS.put(card.getName(), card);

      } catch (Exception e) {
        throw new RuntimeException("Failed to load card: " + cardResource, e);
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
    if (CARDS.containsKey(name)) {
      return CARDS.get(name);
    }
    throw new RuntimeException("Card " + name + " not found!");
  }
}
