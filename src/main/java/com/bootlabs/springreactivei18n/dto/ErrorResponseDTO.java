package com.bootlabs.springreactivei18n.dto;

public record ErrorResponseDTO(
        String code,
        String message,
        String description)
{}
