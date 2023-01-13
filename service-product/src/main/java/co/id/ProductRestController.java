package co.id;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@RestController
public class ProductRestController {

    private final ProductService productService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }
}