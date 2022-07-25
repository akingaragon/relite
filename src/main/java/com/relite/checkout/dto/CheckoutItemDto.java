package com.relite.checkout.dto;

import com.relite.checkout.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutItemDto {
    Item item;
    int quantity;
    BigDecimal unitPrice;
    BigDecimal totalPrice;
}
