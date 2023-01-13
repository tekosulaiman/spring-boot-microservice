package co.id;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/inventories")
@RestController
public class InventoryRestController {

    private final InventoryService inventoryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){
        return inventoryService.isInStock(skuCode);
    }
}