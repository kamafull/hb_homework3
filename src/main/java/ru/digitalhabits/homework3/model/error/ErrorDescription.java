package ru.digitalhabits.homework3.model.error;

import lombok.Data;

@Data
public class ErrorDescription {
    private final String field;
    private final String error;
}