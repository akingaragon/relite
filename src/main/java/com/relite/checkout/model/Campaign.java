package com.relite.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Campaign {
    Item item;
    int quantity;
    BigDecimal price;
}
