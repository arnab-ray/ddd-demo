package io.demo.domain.ports;

import io.demo.domain.common.ListingId;

public interface ICatalogService {
    boolean isValidCatalog(ListingId listingId);
}
