package com.matag.language;

import org.junit.Test;

import static com.matag.language.Plural.plural;
import static org.assertj.core.api.Assertions.assertThat;

public class PluralTest {
  @Test
  public void pluralOfNormalWord() {
    assertThat(plural("WIZARD")).isEqualTo("WIZARDS");
  }

  @Test
  public void pluralOfWordsThatNeedEs() {
    assertThat(plural("JELLYFISH")).isEqualTo("JELLYFISHES");
  }

  @Test
  public void pluralOfWordEndingWithF() {
    assertThat(plural("ELF")).isEqualTo("ELVES");
  }

  @Test
  public void pluralOfIrregularWords() {
    assertThat(plural("CYCLOPS")).isEqualTo("CYCLOPES");
  }
}