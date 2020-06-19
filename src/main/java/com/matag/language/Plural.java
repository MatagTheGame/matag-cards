package com.matag.language;

import java.util.Map;

public class Plural {
  private static final Map<String, String> IRREGULARS = Map.of(
      "CYCLOPS", "CYCLOPES"
  );

  public static String plural(String word) {
    if (IRREGULARS.containsKey(word)) {
      return IRREGULARS.get(word);

    } if (word.endsWith("S") || word.endsWith("SH") || word.endsWith("CH") || word.endsWith("X") || word.endsWith("Z")) {
      return word + "ES";

    } else if (word.endsWith("F")) {
      return replaceLast(word, "F", "VES");

    } else {
      return word + "S";
    }
  }

  private static String replaceLast(String text, String substring, String replacement) {
    return text.replaceFirst("(?s)" + substring + "(?!.*?" + substring + ")", replacement);
  }
}
