package io.demo.infrastructure.controllers;

import io.demo.Either;
import io.demo.catalog.BookDetailsDTO;
import io.demo.domain.Barcode;
import io.demo.domain.ListingId;
import io.demo.domain.NotFoundError;
import io.demo.infrastructure.dtos.AddBookDTO;
import io.demo.service.AddToCatalogService;
import io.demo.service.ISearchCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listing/v1")
public class ListingController {

    private final AddToCatalogService addToCatalogService;
    private final ISearchCatalogService catalogService;

    @Autowired
    public ListingController(AddToCatalogService addToCatalogService, ISearchCatalogService catalogService) {
        this.addToCatalogService = addToCatalogService;
        this.catalogService = catalogService;
    }

    @PostMapping("/book")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void addBook(@RequestBody AddBookDTO addBookDTO) {
        addToCatalogService.execute(
                addBookDTO.title(), new Barcode(addBookDTO.isbn()), addBookDTO.priceInPaise());
    }

    @GetMapping("/book")
    BookDetailsDTO get(
            @RequestParam(required = false) String listingId,
            @RequestParam(required = false) String isbn) {
        Either<BookDetailsDTO, NotFoundError> maybeBook = listingId != null
                ? catalogService.search(new ListingId(listingId))
                : catalogService.search(new Barcode(isbn));

        if (maybeBook.isRight()) {
            throw new IllegalArgumentException(maybeBook.right().message());
        }

        return maybeBook.left();
    }
}
