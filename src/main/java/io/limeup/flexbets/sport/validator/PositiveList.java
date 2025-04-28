package io.limeup.flexbets.sport.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PositiveListValidator.class)
@Target({ ElementType.PARAMETER, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveList {

    String message() default "List must contain only positive numbers and size must not exceed {max}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int max() default 100;

    boolean checkPositive() default true;
}

