package io.limeup.flexbets.sport.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;


public class PositiveListValidator implements ConstraintValidator<PositiveList, List<?>> {

    private int max;
    private boolean checkPositive;

    @Override
    public void initialize(PositiveList constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.checkPositive = constraintAnnotation.checkPositive();
    }

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.size() > max) {
            return false;
        }

        if (!checkPositive) {
            return true;
        }

        return value.stream()
                .filter(Number.class::isInstance)
                .allMatch(item -> ((Number) item).doubleValue() > 0);
    }
}

