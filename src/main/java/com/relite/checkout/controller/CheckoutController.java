package com.relite.checkout.controller;

import com.relite.checkout.dto.CheckoutDto;
import com.relite.checkout.enums.Operation;
import com.relite.checkout.exception.BusinessException;
import com.relite.checkout.exception.ItemNotFoundException;
import com.relite.checkout.services.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    //This endpoint must be POST, but I use GET to give an opportunity to you to call it easily over any browser
    //http://localhost:8081/basket/scan?itemName=A&operation=ADD
    //http://localhost:8081/basket/scan?itemName=A&operation=REMOVE
    //scanItem endpoint should not return cart in best practices but again to be able to show you the result I return cart as the response of this endpoint
    @GetMapping("/scan")
    public CheckoutDto scanItem(@RequestParam String itemName, @RequestParam Operation operation) throws ItemNotFoundException, BusinessException {
        checkoutService.scanItem(itemName, operation);
        return checkoutService.getCart();
    }

}
