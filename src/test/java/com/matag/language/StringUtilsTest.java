package com.matag.language;

import static com.matag.language.StringUtils.replaceLast;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class StringUtilsTest {
  @Test
  public void replaceLastTest() {
    assertThat(replaceLast("cat dog cat", "cat", "other")).isEqualTo("cat dog other");
  }
}