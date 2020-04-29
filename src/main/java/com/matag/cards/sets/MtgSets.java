package com.matag.cards.sets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matag.cards.ResourceLoader;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MtgSets {
  private Map<String, MtgSet> SETS = new LinkedHashMap<>();

  public MtgSets(ObjectMapper objectMapper, ResourceLoader resourceLoader) {
    String[] sets = resourceLoader.getSetsFileNames();
    for (String set : sets) {
      try {
        String setJson = resourceLoader.getSetJson(set);
        MtgSet mtgSet = objectMapper.readValue(setJson, MtgSet.class);
        SETS.put(mtgSet.getCode(), mtgSet);

      } catch (Exception e) {
        throw new RuntimeException("Failed to load set: " + set, e);
      }
    }
  }

  public Map<String, MtgSet> getSets() {
    return SETS;
  }

  public MtgSet getSet(String code) {
    return SETS.get(code);
  }

  public int countSets() {
    return SETS.values().stream()
      .map(set -> set.getCards().size())
      .reduce(Integer::sum)
      .orElse(0);
  }
}
