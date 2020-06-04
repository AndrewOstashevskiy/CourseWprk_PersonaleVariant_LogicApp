package com.controllers;

import com.domain.*;

import com.services.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/market")
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;

    @GetMapping
    public String marketPlace(@AuthenticationPrincipal User user, Model model) {
        marketService.setDataToMarketPage(user, model);
        return "marketPlace";
    }

    @PostMapping("/generate")
    public String generate(@AuthenticationPrincipal User user, Model model) {
        marketService.generateRandomData(user);
        marketService.setDataToMarketPage(user, model);
        return "marketPlace";
    }

    @PostMapping("/add")
    public String addSeligEventFromAdmin(@AuthenticationPrincipal User user,
                                         @RequestParam String description,
                                         @RequestParam String domain,
                                         @RequestParam String price,
                                         Map<String, Object> map
    ) {
        marketService.createProductAndSetDataForPage(user, description, domain, price, map);
        return "redirect:/market";
    }


    @RequestMapping(value = "/buy/{item}", method = RequestMethod.GET)
    public String buyItem(@PathVariable Product item, @AuthenticationPrincipal User user, Model model) {
        if (item.getStatus().equals(ItemStatus.SOLD)) {
            marketService.setErrorMessageWithData(model, ErrorDomain.ALREADY_SOLD);
            marketService.setDataToMarketPage(user, model);
            return "marketPlace";
        } else if (user.getBalance().getBalance() < item.getPrice()) {
            marketService.setErrorMessageWithData(model, ErrorDomain.BALANCE_ERROR);
            marketService.setDataToMarketPage(user, model);
            return "marketPlace";
        } else {
            marketService.buyItem(item, user);
            return "redirect:/user-page";
        }
    }


}
