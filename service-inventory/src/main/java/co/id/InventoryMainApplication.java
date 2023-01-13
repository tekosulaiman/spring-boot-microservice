package co.id;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryMainApplication {
    public static void main(String[] args){
        SpringApplication.run(InventoryMainApplication.class, args);
    }

    /*@Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            Inventory inventory = new Inventory();
            inventory.setSkuCode("iphone_13");
            inventory.setQty(100);

            Inventory inventory1 = new Inventory();
            inventory1.setSkuCode("iphone_13_red");
            inventory1.setQty(0);

            inventoryRepository.save(inventory);
            inventoryRepository.save(inventory1);
        };
    }*/
}