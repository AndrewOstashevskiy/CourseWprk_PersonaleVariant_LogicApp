package com.services;

import com.domain.ItemStatus;
import com.domain.Product;
import com.domain.User;
import com.repositories.ProductRepository;
import com.repositories.UserBalanceRepo;
import com.servise.FiltrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GreetingService {
    private final UserBalanceRepo userBalanceRepo;
    private final FiltrationService filtrationService;
    private final ProductRepository productRepository;

    public void setDataForMainPage(@RequestParam(required = false) String filter,
                                   Map<String,
                                           Object> model, User user) {
        Iterable<Product> productsForSale = filtrationService.filtrateForUser(filter, user);
        Iterable<Product> productsSelling = productRepository.getAllInSelling(user);
        Iterable<Product> soldProducts = productRepository.findByOldOwnerAndSoldTrue(user.getId());
        double balance = userBalanceRepo.findByUserId(user).getBalance();

        model.put("productsForSale", productsForSale);
        model.put("productsSelling", productsSelling);
        model.put("soldProducts", soldProducts);
        model.put("filter", filter);
        model.put("balance", balance);
        model.put("user", user);
    }

    public void placeForSelling(User user, Product item) {
        item.setPlaced(true);
        item.setOldOwner(user.getId());
        item.setDate(LocalDateTime.now());
        item.setStatus(ItemStatus.ACTIVE);

        productRepository.save(item);
    }

    public void setErrorMessage(@RequestParam("id") Product product, Model model) {
        model.addAttribute("msg", "Action is already sold");
        model.addAttribute("item", product);
    }

    public void updateExistingProduct(@RequestParam Map<String, String> form, @RequestParam("id") Product product) {
        product.setDomain(form.get("domain"));
        product.setDescription(form.get("description"));
        product.setDate(LocalDateTime.now());
        product.setPrice(Double.valueOf(form.get("price")));

        productRepository.save(product);
    }
}
