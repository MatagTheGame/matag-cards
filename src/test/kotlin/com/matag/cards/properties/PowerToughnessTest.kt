package com.matag.cards.properties

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class PowerToughnessTest {
    @Test
    fun powerToughnessPositiveTest() {
        // Given
        val powerToughnessString = "+1/+1"

        // When
        val powerToughness = PowerToughness.powerToughness(powerToughnessString)

        // Then
        Assertions.assertThat<PowerToughness?>(powerToughness).isEqualTo(PowerToughness(1, 1))
    }

    @Test
    fun powerToughnessNegativeTest() {
        // Given
        val powerToughnessString = "-1/-1"

        // When
        val powerToughness = PowerToughness.powerToughness(powerToughnessString)

        // Then
        Assertions.assertThat<PowerToughness?>(powerToughness).isEqualTo(PowerToughness(-1, -1))
    }

    @Test
    fun powerToughnessMixedTest() {
        // Given
        val powerToughnessString = "+2/-3"

        // When
        val powerToughness = PowerToughness.powerToughness(powerToughnessString)

        // Then
        Assertions.assertThat<PowerToughness?>(powerToughness).isEqualTo(PowerToughness(2, -3))
    }
}