package com.controllers;

import com.domain.ItemStatus;
import com.domain.Product;
import com.domain.User;
import com.domain.UserBalance;
import com.repositories.ProductRepository;
import com.repositories.UserBalanceRepo;
import com.servise.FiltrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final ProductRepository productRepository;
    private final UserBalanceRepo userBalanceRepo;
    private final FiltrationService filtrationService;

    @RequestMapping(value = "/user-page", method = {RequestMethod.GET, RequestMethod.POST})
    public String getFromMainPage(@RequestParam(required = false) String filter,
                                  Map<String, Object> model,
                                  @AuthenticationPrincipal User user) {
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

        return "main";
    }



    @GetMapping("/user-page/{item}")
    public String editItemData(@PathVariable Product item, Model model) {
        model.addAttribute("item", item);
        return "itemEditor";
    }

    @GetMapping("/user-page/place/{item}")
    public String placeForSelling(@AuthenticationPrincipal User user, @PathVariable Product item) {

        item.setPlaced(true);
        item.setOldOwner(user.getId());
        item.setDate(LocalDateTime.now());
        item.setStatus(ItemStatus.ACTIVE);

        productRepository.save(item);
        return "redirect:/user-page";
    }

    @PostMapping("/user-page/update")
    public String updateItem(@RequestParam Map<String, String> form,
                             @RequestParam("id") Product product,
                             Model model) {

        if (product.isSold()) {
            model.addAttribute("msg", "Action is already sold");
            model.addAttribute("item", product);
            return "itemEditor";
        }
        product.setDomain(form.get("domain"));
        product.setDescription(form.get("description"));
        product.setDate(LocalDateTime.now());
        product.setPrice(Double.valueOf(form.get("price")));

        productRepository.save(product);
        return "redirect:/user-page";
    }
}