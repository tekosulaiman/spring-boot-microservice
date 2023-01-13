package co.id;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @SneakyThrows //Just for demo, Don't use in Production Server
    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
        //log.info("Checking Inventory");
        log.info("Wait Started");
        Thread.sleep(10000);
        log.info("Wait Ended");

        return inventoryRepository
                .findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory ->
                        InventoryResponse
                                .builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQty() > 0)
                                .build()
                ).toList();
    }
}