package com.InteligenciaEletricaAPI.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Validador {
    Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();

    }
}
