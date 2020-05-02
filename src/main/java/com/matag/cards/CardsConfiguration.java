package com.matag.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@ComponentScan(basePackageClasses = {CardsConfiguration.class})
public class CardsConfiguration {
  public static String getResourcesPath() {
    String cd = new File("").getAbsolutePath();
    String matagCardsFolderName = "matag-cards";
    int endPathIndex = cd.lastIndexOf(matagCardsFolderName);
    if (endPathIndex > 0) {
      cd = cd.substring(0, endPathIndex + matagCardsFolderName.length());
    }
    return cd + "/src/main/resources";
  }

  @Bean
  public ObjectMapper cardsObjectMapper() {
    return new ObjectMapper();
  }
}
