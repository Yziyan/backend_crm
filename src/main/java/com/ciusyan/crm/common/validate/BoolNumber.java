package com.ciusyan.crm.common.validate;


import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = BoolNumber.BoolNumberValidator.class)
public @interface BoolNumber {

    String message() default "只能是【0和1】";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class BoolNumberValidator implements ConstraintValidator<BoolNumber, Short> {

        @Override
        public boolean isValid(Short num, ConstraintValidatorContext constraintValidatorContext) {
            return num == null || num == 0 || num == 1;
        }
    }

}
