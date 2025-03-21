package io.demo.service;

import io.demo.Either;
import io.demo.catalog.BookDetailsDTO;
import io.demo.domain.Barcode;
import io.demo.domain.ListingId;
import io.demo.domain.NotFoundError;

public interface ISearchCatalogService {
    Either<BookDetailsDTO, NotFoundError> search(Barcode barcode);

    Either<BookDetailsDTO, NotFoundError> search(ListingId listingId);
}
