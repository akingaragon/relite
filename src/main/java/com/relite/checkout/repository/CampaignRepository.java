package com.relite.checkout.repository;

import com.relite.checkout.model.Campaign;

import java.math.BigDecimal;
import java.util.HashMap;

public class CampaignRepository {

    //This class can be transformed to an interface to implement any database usage
    public static HashMap<String, Campaign> campaigns = new HashMap<>();

    static {
        campaigns.put("A", new Campaign(ItemRepository.items.get("A"), 3, new BigDecimal(130)));
        campaigns.put("B", new Campaign(ItemRepository.items.get("A"), 2, new BigDecimal(45)));
    }
}
