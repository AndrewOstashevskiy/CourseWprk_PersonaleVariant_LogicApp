package com.services;

import com.domain.*;
import com.repositories.ProductRepository;
import com.repositories.UserBalanceRepo;
import com.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final ProductRepository productRepository;
    private final UserBalanceRepo userBalanceRepo;
    private final UserRepository userRepository;

    public void setDataToMarketPage(User user, Model model) {
        Iterable<Product> products = filterForUser(user);
        double balance = userBalanceRepo.findByUserId(user).getBalance();

        model.addAttribute("products", products);
        model.addAttribute("user", user);
        model.addAttribute("balance", balance);
    }

    private Iterable<Product> filterForUser(@AuthenticationPrincipal User user) {
        Iterable<Product> products;
        if (user.getRoles().contains(Role.ADMIN)) {
            products = productRepository.selectAllExceptPending();
        } else {
            products = productRepository.findProductsAvailableToBuy(user, false);
        }
        return products;
    }

    public void createProductAndSetDataForPage(User user,
                                               String description,
                                               String domain,
                                               String price,
                                               Map<String,
                                                       Object> map
    ) {
        Product product = buildProduct(user, description, domain, price);
        productRepository.save(product);

        Iterable<Product> products = productRepository.findAll();

        map.put("products", products);
        map.put("user", user);
    }

    private Product buildProduct(User user,
                                 String description,
                                 String domain,
                                 String price) {
        return Product.builder()
                .description(description)
                .domain(domain)
                .price(Double.valueOf(price))
                .oldOwner(user.getId())
                .owner(user)
                .date(LocalDateTime.now())
                .sold(false)
                .placed(true)
                .status(ItemStatus.ACTIVE)
                .build();
    }

    public void setErrorMessageWithData(@AuthenticationPrincipal User user, Model model) {
        Iterable<Product> products = filterForUser(user);
        model.addAttribute("message", "Sorry... Item already sold");
        model.addAttribute("products", products);
    }

    public void buyItem(@PathVariable Product item, @AuthenticationPrincipal User user) {
        User oldUser = userRepository.getOne(item.getOldOwner());
        Product newItem = productChangeOwner(item, user);

        UserBalance oldUserBalance = payForItem(item, oldUser);
        UserBalance thisUserBalance = chargeForItem(user, newItem);

        userBalanceRepo.saveAll(Arrays.asList(oldUserBalance, thisUserBalance));

        createHistoricalData(item, user);

        saveItems(item, newItem);
    }

    private void saveItems(Product item, Product newItem) {
        productRepository.save(item);
        productRepository.save(newItem);
    }

    private void createHistoricalData(Product item, User user) {
        item.setSold(true);
        item.setPlaced(false);
        item.setOwner(user);
        item.setStatus(ItemStatus.SOLD);
    }

    private UserBalance chargeForItem(User user, Product newItem) {
        UserBalance thisUserBalance = userBalanceRepo.findByUserId(user);
        double thisBalance = thisUserBalance.getBalance();
        thisUserBalance.setBalance(thisBalance - newItem.getPrice());
        return thisUserBalance;
    }

    private UserBalance payForItem(Product item, User oldUser) {
        UserBalance oldUserBalance = userBalanceRepo.findByUserId(oldUser);
        double old = oldUserBalance.getBalance();

        oldUserBalance.setBalance(old + item.getPrice());

        return oldUserBalance;
    }

    private Product productChangeOwner(Product item, User user) {
        return Product.builder()
                .sold(false)
                .placed(false)
                .oldOwner(item.getOwner().getId())
                .owner(user)
                .description(item.getDescription())
                .domain(item.getDomain())
                .price(item.getPrice())
                .date(LocalDateTime.now())
                .status(ItemStatus.PENDING)
                .build();
    }

    public void generateRandomData(User user) {
        List<String> domains = Arrays.asList("GOG", "AWS", "RB", "APP");
        List<String> desc = Arrays.asList("Google", "Amazon", "Ruby", "Apple");
        for (int i = 0; i < domains.size() ; i++) {
            for (int j = 0; j < 10; j++) {
                Product product = Product.builder()
                        .description(desc.get(i))
                        .domain(domains.get(i))
                        .price(ThreadLocalRandom.current().nextDouble(0.0001, 4))
                        .oldOwner(user.getId())
                        .owner(user)
                        .date(LocalDateTime.now())
                        .sold(false)
                        .placed(true)
                        .status(ItemStatus.ACTIVE)
                        .build();
                productRepository.save(product);
            }
        }
    }
}
