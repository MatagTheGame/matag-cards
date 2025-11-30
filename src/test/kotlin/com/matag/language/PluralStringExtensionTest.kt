package com.matag.language

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PluralStringExtensionTest {
    @ParameterizedTest
    @CsvSource(
        "WIZARD,WIZARDS",
        "JELLYFISH,JELLYFISHES",
        "ELF,ELVES",
        "CYCLOPS,CYCLOPES"
    )
    fun `test plurals`(word: String, plural: String) {
        assertThat(word.plural()).isEqualTo(plural)
    }
}