package com.relite.checkout.services;

import com.relite.checkout.dto.CheckoutDto;
import com.relite.checkout.dto.CheckoutItemDto;
import com.relite.checkout.enums.Operation;
import com.relite.checkout.exception.BusinessException;
import com.relite.checkout.exception.ItemNotFoundException;
import com.relite.checkout.model.Campaign;
import com.relite.checkout.model.Item;
import com.relite.checkout.repository.CampaignRepository;
import com.relite.checkout.repository.ItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CheckoutService {


    //basket should be implemented to work with sessions, here I ignore sessions and used basic approach
    HashMap<String, Integer> basket;

    public void scanItem(String itemName, Operation operation) throws ItemNotFoundException, BusinessException {
        if (!ItemRepository.items.containsKey(itemName))
            throw new ItemNotFoundException(String.format("Item %s can not be found in warehouse!", itemName));

        Integer quantity = basket.get(itemName);

        switch (operation) {
            case ADD -> {
                if (basket.containsKey(itemName)) {
                    basket.put(itemName, quantity + 1);
                } else {
                    basket.put(itemName, 1);
                }
            }
            case REMOVE -> {
                if (quantity == null) {
                    log.warn(String.format("Unexpected %s request", operation));
                    return;
                }
                if (quantity > 1) {
                    basket.put(itemName, quantity - 1);
                } else if (quantity == 1) {
                    basket.remove(itemName);
                }
            }
            default -> throw new BusinessException(String.format("%s operation not found", operation));
        }
    }

    public CheckoutDto getCart() {
        CheckoutDto checkoutDto = new CheckoutDto();
        List<CheckoutItemDto> items = new ArrayList<>();
        basket.forEach((key, value) -> items.add(createCheckoutItem(key)));
        checkoutDto.setItems(items);
        checkoutDto.setTotal(items.stream().map(CheckoutItemDto::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        return checkoutDto;
    }

    private CheckoutItemDto createCheckoutItem(String itemName) {
        Item item = ItemRepository.items.get(itemName);
        BigDecimal total;
        Integer basketQuantity = basket.get(itemName);
        if (CampaignRepository.campaigns.containsKey(itemName)) {
            Campaign campaign = CampaignRepository.campaigns.get(itemName);
            if (campaign.getQuantity() < basketQuantity) {
                total = item.getPrice().multiply(new BigDecimal(basketQuantity % campaign.getQuantity())).add(campaign.getPrice().multiply(new BigDecimal(basketQuantity / campaign.getQuantity())));
            } else if (campaign.getQuantity() == basketQuantity) {
                total = campaign.getPrice();
            } else {
                total = item.getPrice().multiply(new BigDecimal(basketQuantity));
            }
        } else {
            total = item.getPrice().multiply(new BigDecimal(basketQuantity));
        }
        return new CheckoutItemDto(item, basketQuantity, item.getPrice(), total);
    }

    static {

    }
}
