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

    private static final DecimalFormat US_DECIMAL_FORMAT;

    static {
        DecimalFormatSymbols usSymbols = new DecimalFormatSymbols(Locale.US);
        usSymbols.setDecimalSeparator('.');
        US_DECIMAL_FORMAT = new DecimalFormat("#0.00", usSymbols);
    }

    public static String convertWeightToPreferredUnit(double weightInKg, boolean convertToImperial, Unit<Mass> unit) {
        if (!convertToImperial) {
            return US_DECIMAL_FORMAT.format(weightInKg);
        }

        Quantity<Mass> weightMetric = Quantities.getQuantity(weightInKg, Units.KILOGRAM);
        Quantity<Mass> weightImperial = weightMetric.to(unit);
        return US_DECIMAL_FORMAT.format(weightImperial.getValue().doubleValue());
    }

    public static String convertHeightToPreferredUnit(double heightInCm, boolean convertToImperial, Unit<Length> unit) {
        if (!convertToImperial) {
            return US_DECIMAL_FORMAT.format(heightInCm);
        }

        Quantity<Length> heightMetric = Quantities.getQuantity(heightInCm, CENTI(Units.METRE));
        Quantity<Length> heightImperial = heightMetric.to(unit);
        return US_DECIMAL_FORMAT.format(heightImperial.getValue().doubleValue());
    }

}
