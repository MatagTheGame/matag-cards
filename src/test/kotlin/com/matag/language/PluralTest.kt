package com.matag.language

import com.matag.language.Plural.plural
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PluralTest {
    @Test
    fun pluralOfNormalWord() {
        Assertions.assertThat(plural("WIZARD")).isEqualTo("WIZARDS")
    }

    @Test
    fun pluralOfWordsThatNeedEs() {
        Assertions.assertThat(plural("JELLYFISH")).isEqualTo("JELLYFISHES")
    }

    @Test
    fun pluralOfWordEndingWithF() {
        Assertions.assertThat(plural("ELF")).isEqualTo("ELVES")
    }

    @Test
    fun pluralOfIrregularWords() {
        Assertions.assertThat(plural("CYCLOPS")).isEqualTo("CYCLOPES")
    }
}