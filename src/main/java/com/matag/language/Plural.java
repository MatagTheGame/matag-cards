package com.matag.language;

public class Plural {
  public static String plural(String word) {
    if (word.endsWith("f")) {
      return replaceLast(word, "f", "ves");

    } else {
      return word + "s";
    }
  }

  private static String replaceLast(String text, String substring, String replacement) {
    return text.replaceFirst("(?s)" + substring + "(?!.*?" + substring + ")", replacement);
  }
}
