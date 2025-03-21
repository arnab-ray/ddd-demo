package io.demo.catalog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDetailsDTO(String listingId, String title, String isbn, Integer priceInPaise) {
}
