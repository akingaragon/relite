package com.relite.checkout;

import com.relite.checkout.enums.Operation;
import com.relite.checkout.exception.BusinessException;
import com.relite.checkout.exception.ItemNotFoundException;
import com.relite.checkout.services.CheckoutService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
class ReliteApplicationTests {

    private CheckoutService checkoutService;

    @BeforeEach
    void setUp() {
        checkoutService = new CheckoutService(new HashMap<>());
    }

    @Test
    void scanItemAddOperationWithoutCampaignTest() throws BusinessException, ItemNotFoundException {
        checkoutService.scanItem("A", Operation.ADD);
        Assertions.assertEquals(1, checkoutService.getCart().getItems().size());
        Assertions.assertEquals(1, checkoutService.getCart().getItems().get(0).getQuantity());
        Assertions.assertEquals(new BigDecimal(50), checkoutService.getCart().getTotal());
    }

    @Test
    void scanItemAddOperationWithCampaignTest() throws BusinessException, ItemNotFoundException {
        checkoutService.scanItem("A", Operation.ADD);
        checkoutService.scanItem("A", Operation.ADD);
        checkoutService.scanItem("A", Operation.ADD);
        Assertions.assertEquals(1, checkoutService.getCart().getItems().size());
        Assertions.assertEquals(3, checkoutService.getCart().getItems().get(0).getQuantity());
        Assertions.assertEquals(new BigDecimal(130), checkoutService.getCart().getTotal());
    }

    @Test
    void scanItemAddOperationWithCampaignForMultipleScannedItemTest() throws BusinessException, ItemNotFoundException {
        checkoutService.scanItem("A", Operation.ADD);
        checkoutService.scanItem("A", Operation.ADD);
        checkoutService.scanItem("A", Operation.ADD);
        checkoutService.scanItem("B", Operation.ADD);
        checkoutService.scanItem("B", Operation.ADD);
        Assertions.assertEquals(2, checkoutService.getCart().getItems().size());
        Assertions.assertEquals(3, checkoutService.getCart().getItems().get(0).getQuantity());
        Assertions.assertEquals(2, checkoutService.getCart().getItems().get(1).getQuantity());
        Assertions.assertEquals(new BigDecimal(175), checkoutService.getCart().getTotal());
    }

    @Test
    void scanItemRemoveOperationTest() throws BusinessException, ItemNotFoundException {
        checkoutService.scanItem("A", Operation.ADD);
        checkoutService.scanItem("A", Operation.ADD);
        checkoutService.scanItem("A", Operation.REMOVE);
        Assertions.assertEquals(1, checkoutService.getCart().getItems().size());
    }

    @Test
    void scanNotExistItemAddOperationTest() {
        Assertions.assertThrows(ItemNotFoundException.class, () -> checkoutService.scanItem("E", Operation.ADD));
    }

}
