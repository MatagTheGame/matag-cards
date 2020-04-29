package com.matag.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class Cards {
  private Map<String, Card> CARDS = new LinkedHashMap<>();

  public Cards(ObjectMapper objectMapper, ResourceLoader resourceLoader) {
    Resource[] cardResources = resourceLoader.getCardsFileNames();
    for (Resource cardResource : cardResources) {
      try {
        Card card = objectMapper.readValue(cardResource.getInputStream(), Card.class);
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
    if (CARDS.containsKey(name)) {
      return CARDS.get(name);
    }
    throw new RuntimeException("Card " + name + " not found!");
  }
}
