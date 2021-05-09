package com.matag.cards;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan(basePackageClasses = {CardsConfiguration.class})
public class CardsConfiguration {
  public static String getResourcesPath() {
    return new File("src/main/resources").getAbsolutePath();
  }

  @Bean
  public ObjectMapper cardsObjectMapper() {
    return new ObjectMapper();
  }
}
