package io.demo.domain;

import io.demo.catalog.BookDetailsDTO;

public class BookDetailsMapper {
    public BookDetailsDTO getBookDetails(Book book) {
        return new BookDetailsDTO(book.getListingId(), book.getTitle(), book.getBarcode().value(), book.getPriceInPaise());
    }
}
