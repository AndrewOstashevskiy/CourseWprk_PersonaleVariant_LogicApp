package com.servise;

import com.domain.ItemStatus;
import com.domain.Product;
import com.domain.User;
import com.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class FiltrationService {

    private final ProductRepository productRepository;

    public Iterable<Product> filtrateForUser(@RequestParam(required = false) String filter,
                                             @AuthenticationPrincipal User user) {
        Iterable<Product> productsForSale;
        if (filter == null || filter.isEmpty()) {
            productsForSale = productRepository.findByOwnerAndStatus(user, ItemStatus.PENDING);
        } else {
            productsForSale = productRepository.findByDomainAndOwnerAndSoldFalse(filter, user);
        }
        return productsForSale;
    }
}
