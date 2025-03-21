package io.demo.infrastructure.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AddBookDTO(String title, String isbn, Integer priceInPaise) {}
