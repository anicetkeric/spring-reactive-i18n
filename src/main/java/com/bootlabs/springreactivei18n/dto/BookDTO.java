package com.bootlabs.springreactivei18n.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookDTO(
        Long id,
        @NotNull @NotEmpty String title,
        @NotNull String isbn,
        String description,
        @Min(10)
        int page,
        double price
) {
}
