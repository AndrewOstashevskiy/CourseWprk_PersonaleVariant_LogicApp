package com.controllers;

import com.domain.Product;
import com.domain.User;
import com.services.GreetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final GreetingService greetingService;

    @RequestMapping(value = "/user-page", method = {RequestMethod.GET, RequestMethod.POST})
    public String getFromMainPage(@RequestParam(required = false) String filter,
                                  Map<String, Object> model,
                                  @AuthenticationPrincipal User user) {
        greetingService.setDataForMainPage(filter, model, user);
        return "main";
    }


    @GetMapping("/user-page/{item}")
    public String editItemData(@PathVariable Product item, Model model) {
        model.addAttribute("item", item);
        return "itemEditor";
    }

    @GetMapping("/user-page/place/{item}")
    public String placeForSelling(@AuthenticationPrincipal User user,
                                  @PathVariable Product item) {
        greetingService.placeForSelling(user, item);
        return "redirect:/user-page";
    }

    @GetMapping("/user-page/stop/{item}")
    public String stopSelling(@AuthenticationPrincipal User user, @PathVariable Product item) {
        greetingService.stopSelling(user, item);
        return "redirect:/user-page";
    }

    @PostMapping("/user-page/update")
    public String updateItem(@RequestParam Map<String, String> form,
                             @RequestParam("id") Product product,
                             Model model) {

        if (product.isSold()) {
            greetingService.setErrorMessage(product, model);
            return "itemEditor";
        }
        greetingService.updateExistingProduct(form, product);
        return "redirect:/user-page";
    }
}