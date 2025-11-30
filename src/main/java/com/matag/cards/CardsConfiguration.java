package com.matag.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonObjectMapper;

@Configuration
@ComponentScan(basePackageClasses = {CardsConfiguration.class})
public class CardsConfiguration {
  public static String getResourcesPath() {
    return new File("src/main/resources").getAbsolutePath();
  }

  @Bean
  public ObjectMapper cardsObjectMapper() {
    return jacksonObjectMapper();
  }
}
