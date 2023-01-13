package co.id;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@RestController
public class OrderRestController {

    private final OrderService orderService;

    @Retry(name = "inventory")
    @TimeLimiter(name = "inventory")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){

        return CompletableFuture.supplyAsync(() ->orderService.placeOrder(orderRequest));
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(() ->"Something wrong, Please order after some time");
    }
}