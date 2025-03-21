package io.demo.catalog;

public interface CatalogClient {
    BookDetailsDTO getBookDetails(String isbn);

    BookDetailsDTO getBookDetailsByListingId(String listingId);
}
