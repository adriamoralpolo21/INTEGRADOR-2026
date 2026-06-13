package com.glovo.app.validation;

import com.glovo.app.repository.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, String> {
    private final UsuarioRepository repo;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        String tel = value.replaceAll("\\D", "");
        return !repo.existsByTelefono(tel);
    }
}
