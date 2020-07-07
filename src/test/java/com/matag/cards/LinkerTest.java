package com.matag.cards;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.matag.cards.sets.MtgSets;
import com.matag.downloader.CardScryFallLinker;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.matag.cards.properties.Type.BASIC;
import static java.util.stream.Collectors.toList;

@RunWith(SpringRunner.class)
@Import(CardsConfiguration.class)
public class LinkerTest {

  @Autowired
  private Cards cards;

  @Autowired
  private MtgSets mtgSets;

  @Test
  public void scryFallLinker() throws Exception {
    var cardsObjectMapper = createCardsObjectMapper();
    var setsObjectMapper = createSetsObjectMapper();

    var sets = mtgSets.getSets();

    var cardsToLink = cards.getAll().stream()
      .filter(card -> StringUtils.isBlank(card.getImageUrl()))
      .collect(toList());

    for (int i = 0; i < cardsToLink.size(); i++) {
      var card = cardsToLink.get(i);
      var cardScryFallLinker = new CardScryFallLinker(card);
      var updatedCard = card.toBuilder()
          .imageUrl(cardScryFallLinker.getImage())
          .types(cardScryFallLinker.getTypes())
          .subtypes(cardScryFallLinker.getSubtypes())
          .power(cardScryFallLinker.getPower())
          .toughness(cardScryFallLinker.getToughness())
          .rarity(cardScryFallLinker.getRarity())
          .ruleText(cardScryFallLinker.getOracleText())
          .colors(cardScryFallLinker.getColors())
          .cost(cardScryFallLinker.getManaCost())
          .build();
      var cardJson = cardsObjectMapper.writeValueAsString(updatedCard);
      Files.writeString(Paths.get(CardsConfiguration.getResourcesPath() + "/cards/" + card.getName() + ".json"), cardJson);
      System.out.println("Downloaded " + (i + 1) + " of " + cardsToLink.size() + " -> " + card.getName());

      if (!card.getTypes().contains(BASIC)) {
        for (String scryFallSet : cardScryFallLinker.getSets()) {
          if (sets.containsKey(scryFallSet)) {
            sets.get(scryFallSet).getCards().add(card.getName());
            var setJson = setsObjectMapper.writeValueAsString(sets.get(scryFallSet));
            Files.writeString(Paths.get(CardsConfiguration.getResourcesPath() + "/sets/" + scryFallSet + ".json"), setJson);
          }
        }
      }
    }
  }

  public ObjectMapper createCardsObjectMapper() {
    var objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
    return objectMapper;
  }

  public ObjectMapper createSetsObjectMapper() {
    var objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
    prettyPrinter.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE);
    objectMapper.setDefaultPrettyPrinter(prettyPrinter);
    return objectMapper;
  }
}
