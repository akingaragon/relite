package com.relite.checkout.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CheckoutDto {
    List<CheckoutItemDto> items;
    BigDecimal total;
}
