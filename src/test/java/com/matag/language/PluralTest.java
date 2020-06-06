package com.matag.language;

import org.junit.Test;

import static com.matag.language.Plural.plural;
import static org.assertj.core.api.Assertions.assertThat;

public class PluralTest {
  @Test
  public void pluralOfNormalWord() {
    assertThat(plural("wizard")).isEqualTo("wizards");
  }

  @Test
  public void pluralOfNormalElf() {
    assertThat(plural("elf")).isEqualTo("elves");
  }
}