package com.glovo.app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniquePhoneValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniquePhone {
    String message() default "Ese número ya está registrado.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
