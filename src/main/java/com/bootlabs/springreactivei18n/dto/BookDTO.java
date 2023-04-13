package com.bootlabs.springreactivei18n.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;

public record BookDTO(
        Long id,
        @NonNull @NotEmpty String title,
        @NonNull String isbn,
        String description,
        int page,
        double price
) {
}
