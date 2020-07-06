package com.matag.cards.properties;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PowerToughnessTest {
    @Test
    public void powerToughnessPositiveTest() {
        // Given
        var powerToughnessString = "+1/+1";

        // When
        var powerToughness = PowerToughness.powerToughness(powerToughnessString);

        // Then
        assertThat(powerToughness).isEqualTo(new PowerToughness(1, 1));
    }

    @Test
    public void powerToughnessNegativeTest() {
        // Given
        var powerToughnessString = "-1/-1";

        // When
        var powerToughness = PowerToughness.powerToughness(powerToughnessString);

        // Then
        assertThat(powerToughness).isEqualTo(new PowerToughness(-1, -1));
    }

    @Test
    public void powerToughnessMixedTest() {
        // Given
        var powerToughnessString = "+2/-3";

        // When
        var powerToughness = PowerToughness.powerToughness(powerToughnessString);

        // Then
        assertThat(powerToughness).isEqualTo(new PowerToughness(2, -3));
    }
}