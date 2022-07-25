package com.relite.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Item {
    String name;
    BigDecimal price;
}
