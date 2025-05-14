package io.limeup.flexbets.sport.utils;

import static org.assertj.core.api.Assertions.assertThat;

import systems.uom.common.USCustomary;

import org.junit.jupiter.api.Test;

class UnitConversionUtilsTest {

    @Test
    void convertWeightToPreferredUnitShouldReturnMetricValueWhenNotConvertingToImperial() {
        double weightInKg = 70.5;
        String result = UnitConversionUtils.convertWeightToPreferredUnit(weightInKg, false, USCustomary.POUND);
        assertThat(result).isEqualTo("70.5");
    }

    @Test
    void convertWeightToPreferredUnitShouldConvertToPoundsWhenConvertingToImperial() {
        double weightInKg = 70.0;
        String result = UnitConversionUtils.convertWeightToPreferredUnit(weightInKg, true, USCustomary.POUND);
        assertThat(result).isEqualTo("154.3");
    }

    @Test
    void convertHeightToPreferredUnitShouldReturnMetricValueWhenNotConvertingToImperial() {
        double heightInCm = 180.0;
        String result = UnitConversionUtils.convertHeightToPreferredUnit(heightInCm, false, USCustomary.INCH);
        assertThat(result).isEqualTo("180.0");
    }

    @Test
    void convertHeightToPreferredUnitShouldConvertToInchesWhenConvertingToImperial() {
        double heightInCm = 180.0;
        String result = UnitConversionUtils.convertHeightToPreferredUnit(heightInCm, true, USCustomary.INCH);
        assertThat(result).isEqualTo("70.87");
    }

}

