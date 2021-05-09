package com.matag.cards.properties;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@AllArgsConstructor
@JsonDeserialize(builder = CardImageUrls.CardImageUrlsBuilder.class)
@Builder
public class CardImageUrls {
  String lowResolution;
  String highResolution;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CardImageUrlsBuilder {}
}
