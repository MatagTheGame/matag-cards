package com.matag.language

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReplaceLastStringExtensionTest {
    @Test
    fun replaceLastTest() {
        assertThat("cat dog cat".replaceLast( "cat", "other")).isEqualTo("cat dog other")
    }
}