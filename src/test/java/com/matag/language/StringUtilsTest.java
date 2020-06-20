package com.matag.language;

import org.junit.Test;

import static com.matag.language.StringUtils.replaceLast;
import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {
  @Test
  public void replaceLastTest() {
    assertThat(replaceLast("cat dog cat", "cat", "other")).isEqualTo("cat dog other");
  }
}