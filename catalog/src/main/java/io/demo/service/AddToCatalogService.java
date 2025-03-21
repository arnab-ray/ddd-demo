package io.demo.service;

import io.demo.Either;
import io.demo.catalog.BookDetailsDTO;
import io.demo.domain.Barcode;
import io.demo.domain.Book;
import io.demo.domain.BookRepository;
import io.demo.domain.NotFoundError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddToCatalogService {
    private final ISearchCatalogService searchCatalogService;
    private final BookRepository bookRepository;

    @Autowired
    public AddToCatalogService(ISearchCatalogService searchCatalogService, BookRepository bookRepository) {
        this.searchCatalogService = searchCatalogService;
        this.bookRepository = bookRepository;
    }

    public void execute(String title, Barcode barcode, Integer price) {
        Either<BookDetailsDTO, NotFoundError> existentBook = searchCatalogService.search(barcode);
        if (existentBook.isRight()) {
            var book = new Book(title, barcode, price);
            bookRepository.save(book);
        }
    }
}
