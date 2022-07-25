package com.relite.checkout.repository;

import com.relite.checkout.model.Item;

import java.math.BigDecimal;
import java.util.HashMap;

public class ItemRepository {
    //This class can be transformed to an interface to implement any database usage

    public static HashMap<String, Item> items = new HashMap<>();

    static {
        items.put("A", new Item("A", new BigDecimal("50")));
        items.put("B", new Item("B", new BigDecimal("30")));
        items.put("C", new Item("C", new BigDecimal("20")));
        items.put("D", new Item("D", new BigDecimal("15")));
    }
}
