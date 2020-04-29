package com.matag.cards;

import java.io.InputStream;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class ResourceLoader {

  public String[] getCardsFileNames() {
    return asString(getResourceAsStream("cards")).split("\n");
  }

  public String[] getSetsFileNames() {
    return asString(getResourceAsStream("sets")).split("\n");
  }

  public String getCardJson(String cardName) {
    return asString(getResourceAsStream("cards/" + cardName));
  }

  public String getSetJson(String setName) {
    return asString(getResourceAsStream("sets/" + setName));
  }

  private InputStream getResourceAsStream(String resource) {
    InputStream in = getContextClassLoader().getResourceAsStream(resource);
    return in == null ? getClass().getResourceAsStream(resource) : in;
  }

  private String asString(InputStream inputStream) {
    Scanner s = new Scanner(inputStream).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }

  private ClassLoader getContextClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }
}
