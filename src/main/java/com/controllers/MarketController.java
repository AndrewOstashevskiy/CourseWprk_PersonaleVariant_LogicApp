package com.controllers;

import com.domain.ItemStatus;
import com.domain.Product;
import com.domain.Role;
import com.domain.User;
import com.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping("/market")
@RequiredArgsConstructor
public class MarketController {

    private final ProductRepository productRepository;

    @GetMapping
    public String marketPlace(@AuthenticationPrincipal User user, Model model) {
        Iterable<Product> products = filterForUser(user);
        model.addAttribute("products", products);
        model.addAttribute("user", user);
        return "marketPlace";
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

    @PostMapping("/add")
    public String addSeligEventFromAdmin(@AuthenticationPrincipal User user,
                                         @RequestParam String description,
                                         @RequestParam String domain,
                                         @RequestParam String price,
                                         Map<String, Object> map
    ) {
        Product product = Product.builder()
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
        productRepository.save(product);

        Iterable<Product> products = productRepository.findAll();

        map.put("products", products);
        map.put("user", user);
        return "redirect:/market";
    }

    @RequestMapping(value = "/buy/{item}", method = RequestMethod.GET)
    public String buyItem(@PathVariable Product item, @AuthenticationPrincipal User user, Model model) {

        if (item.getStatus().equals(ItemStatus.SOLD)) {
            Iterable<Product> products = filterForUser(user);
            model.addAttribute("message", "Item already sold");
            model.addAttribute("products", products);
            return "marketPlace";
        }

        Product newItem = Product.builder()
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

        item.setSold(true);
        item.setPlaced(false);
        item.setOwner(user);
        item.setStatus(ItemStatus.SOLD);

        productRepository.save(item);
        productRepository.save(newItem);

        return "redirect:/user-page";
    }
}
