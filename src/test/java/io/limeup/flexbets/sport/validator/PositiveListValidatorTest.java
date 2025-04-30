package io.limeup.flexbets.sport.validator;

import jakarta.validation.Payload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PositiveListValidatorTest {

    private PositiveListValidator validator = new PositiveListValidator();

    @BeforeEach
    void setUp() {
        PositiveList annotation = new PositiveList() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return PositiveList.class;
            }

            @Override
            public int max() {
                return 3;
            }

            @Override
            public boolean checkPositive() {
                return true;
            }

            @Override
            public String message() {
                return "Invalid list";
            }

            @Override
            public Class<?>[] groups() {
                return new Class[0];
            }

            @Override
            public Class<? extends Payload>[] payload() {
                return new Class[0];
            }
        };

        validator.initialize(annotation);
    }

    @Test
    void shouldReturnTrueForNullList() {
        assertThat(validator.isValid(null, null)).isTrue();
    }

    @Test
    void shouldReturnTrueForValidPositiveList() {
        List<Integer> list = List.of(1, 2, 3);
        assertThat(validator.isValid(list, null)).isTrue();
    }

    @Test
    void shouldReturnFalseForListWithNegativeNumber() {
        List<Integer> list = List.of(1, -2, 3);
        assertThat(validator.isValid(list, null)).isFalse();
    }

    @Test
    void shouldReturnFalseForListExceedingMaxSize() {
        List<Integer> list = List.of(1, 2, 3, 4);
        assertThat(validator.isValid(list, null)).isFalse();
    }

    @Test
    void shouldReturnTrueWhenCheckPositiveFalseAndNegativePresent() {
        PositiveListValidator noCheckValidator = new PositiveListValidator();
        PositiveList annotation = new PositiveList() {
            @Override public Class<? extends Annotation> annotationType() { return PositiveList.class; }
            @Override public int max() { return 5; }
            @Override public boolean checkPositive() { return false; }
            @Override public String message() { return "Invalid list"; }
            @Override public Class<?>[] groups() { return new Class[0]; }
            @Override public Class<? extends Payload>[] payload() { return new Class[0]; }
        };

        noCheckValidator.initialize(annotation);
        List<Integer> list = List.of(-1, -2, -3);
        assertThat(noCheckValidator.isValid(list, null)).isTrue();
    }
}

