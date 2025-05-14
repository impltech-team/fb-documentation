package io.limeup.flexbets.sport.utils;

import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static javax.measure.MetricPrefix.CENTI;

public class UnitConversionUtils {

    private UnitConversionUtils(){}

    public static String convertWeightToPreferredUnit(double weightInKg, boolean convertToImperial, Unit<Mass> unit) {
        if (!convertToImperial) {
            return String.valueOf(weightInKg);
        }

        Quantity<Mass> weightMetric = Quantities.getQuantity(weightInKg, Units.KILOGRAM);
        Quantity<Mass> weightImperial = weightMetric.to(unit);
        return formatNumber(weightImperial.getValue().doubleValue(), 1);
    }

    public static String convertHeightToPreferredUnit(double heightInCm, boolean convertToImperial, Unit<Length> unit) {
        if (!convertToImperial) {
            return String.valueOf(heightInCm);
        }

        Quantity<Length> heightMetric = Quantities.getQuantity(heightInCm, CENTI(Units.METRE));
        Quantity<Length> heightImperial = heightMetric.to(unit);
        return formatNumber(heightImperial.getValue().doubleValue(), 2);
    }

    private static String formatNumber(double value, int fractionDigits) {
        DecimalFormatSymbols usSymbols = new DecimalFormatSymbols(Locale.US);
        usSymbols.setDecimalSeparator('.');
        StringBuilder pattern = new StringBuilder("0");
        if (fractionDigits > 0) {
            pattern.append(".");
            pattern.append("0".repeat(fractionDigits));
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern.toString(), usSymbols);
        return decimalFormat.format(value);
    }

}
