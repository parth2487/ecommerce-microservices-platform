package com.aixtor.ecommerce.product.service.controller;


import com.aixtor.ecommerce.product.service.model.Product;
import com.aixtor.ecommerce.product.service.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

//    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
//        log.info("Received request to create product");
        return service.save(product);
    }

    @PostMapping("/bulk")
    public List<Product> createBulk(@RequestBody List<Product> products) {
        return service.saveAll(products);
    }


    @GetMapping
    public List<Product> getAll() {
//        log.info("Received request to fetch all products");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable String id) {
//        log.info("Received request to fetch product {}", id);
        return service.getById(id);
    }


    @PutMapping("/{id}")
    public Product update(@PathVariable String id, @RequestBody Product productDetails) {
        Product existingProduct = service.getById(id);
        if (existingProduct == null) {
            return null; // You can also throw a custom exception here
        }

        // Update fields
        existingProduct.setName(productDetails.getName());
        existingProduct.setBrand(productDetails.getBrand());
        existingProduct.setCategory(productDetails.getCategory());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setAttributes(productDetails.getAttributes());
        existingProduct.setImages(productDetails.getImages());
        existingProduct.setVariants(productDetails.getVariants());

        return service.save(existingProduct);
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        Product existingProduct = service.getById(id);
        if (existingProduct == null) {
            return "Product not found with id: " + id;
        }

        service.delete(id);
        return "Product deleted successfully with id: " + id;
    }

    @GetMapping("/{id}/price")
    public Double getProductPrice(@PathVariable String id) {
        Product product = service.getById(id);
        if (product == null) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        return product.getPrice();
    }

//    @GetMapping("/{id}/variants/{variantId}/stock")
//    public Integer getVariantStock(@PathVariable String id, @PathVariable String variantId) {
//        Product product = service.getById(id);
//        if (product == null) {
//            throw new RuntimeException("Product not found with id: " + id);
//        }
//        return product.getVariants().stream().filter(v -> v.getVariantId().equals(variantId)).findFirst().map(Variant::getStock).orElseThrow(() -> new RuntimeException("Variant not found: " + variantId));
//    }

    // ✅ Get price for a specific variant (if you want per-variant pricing in future)
    @GetMapping("/{id}/variants/{variantId}/price")
    public Double getVariantPrice(@PathVariable String id, @PathVariable String variantId) {
        Product product = service.getById(id);
        if (product == null) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        // for now, variant price = base product price
        return product.getPrice();
    }


//    @PutMapping("/{id}/variants/{variantId}/stock")
//    public Variant updateVariantStock(@PathVariable String id, @PathVariable String variantId, @RequestParam int quantityToReduce) {
//        Product product = service.getById(id);
//        if (product == null) {
//            throw new RuntimeException("Product not found: " + id);
//        }
//
//        Variant variant = product.getVariants().stream().filter(v -> v.getVariantId().equals(variantId)).findFirst().orElseThrow(() -> new RuntimeException("Variant not found: " + variantId));
//
//        if (variant.getStock() < quantityToReduce) {
//            throw new RuntimeException("Not enough stock for variant: " + variantId);
//        }
//
//        variant.setStock(variant.getStock() - quantityToReduce);
//        service.save(product); // save updated product
//        return variant;
//    }
}
