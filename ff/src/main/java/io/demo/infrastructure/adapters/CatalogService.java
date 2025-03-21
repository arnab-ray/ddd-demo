package io.demo.infrastructure.adapters;

import io.demo.catalog.CatalogClient;
import io.demo.domain.common.ListingId;
import io.demo.domain.ports.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatalogService implements ICatalogService {

    private final CatalogClient catalogClient;

    @Autowired
    public CatalogService(CatalogClient catalogClient) {
        this.catalogClient = catalogClient;
    }

    @Override
    public boolean isValidCatalog(ListingId listingId) {
        var response = catalogClient.getBookDetailsByListingId(listingId.id());
        return response != null;
    }
}
