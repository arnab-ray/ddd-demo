package io.demo.service;

import io.demo.Either;
import io.demo.Left;
import io.demo.Right;
import io.demo.catalog.BookDetailsDTO;
import io.demo.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchCatalogService implements ISearchCatalogService {

    private final BookRepository bookRepository;

    @Autowired
    public SearchCatalogService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Either<BookDetailsDTO, NotFoundError> search(Barcode barcode) {
        var maybeBook = bookRepository.findByBarcode(barcode);
        return maybeBook.isPresent()
                ? new Left<>(new BookDetailsMapper().getBookDetails(maybeBook.get()))
                : new Right<>(new NotFoundError("Book not found"));
    }

    @Override
    public Either<BookDetailsDTO, NotFoundError> search(ListingId listingId) {
        var maybeBook = bookRepository.findByListingId(listingId);
        return maybeBook.isPresent()
                ? new Left<>(new BookDetailsMapper().getBookDetails(maybeBook.get()))
                : new Right<>(new NotFoundError("Book not found"));
    }
}
