package co.id;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItem> orderItems = orderRequest.getOrderItems()
                .stream()
                .map(this::mapToDTO)
                .toList();

        order.setOrderItems(orderItems);

        List<String> skuCodes = order.getOrderItems().stream()
                .map(OrderItem::getSkuCode)
                .toList();

        // todo: Call Inventory Service, and place order if product is in stock
        InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                //.uri("http://localhost:8082/api/v1/inventories",
                .uri("http://service-inventory/api/v1/inventories",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsIn = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);

        if(allProductsIn){
            orderRepository.save(order);

            return "Order Placed Successfully";
        }else{
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

    private OrderItem mapToDTO(OrderItemDTO orderItemDTO){
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuCode(orderItemDTO.getSkuCode());
        orderItem.setPrice(orderItemDTO.getPrice());
        orderItem.setQty(orderItemDTO.getQty());

        return orderItem;
    }
}