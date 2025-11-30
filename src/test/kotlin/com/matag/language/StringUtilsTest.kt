package com.matag.language

import com.matag.language.StringUtils.replaceLast
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class StringUtilsTest {
    @Test
    fun replaceLastTest() {
        assertThat(replaceLast("cat dog cat", "cat", "other")).isEqualTo("cat dog other")
    }
}