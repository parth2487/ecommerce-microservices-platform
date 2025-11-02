package com.aixtor.ecommerce.product.service.service;

import com.aixtor.ecommerce.product.service.model.Product;
import com.aixtor.ecommerce.product.service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

//    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final com.aixtor.ecommerce.product.service.repository.ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product save(Product product) {
//        log.info("Saving product: {}", product.getName());
        return repository.save(product);
    }

    public List<Product> getAll() {
//        log.info("Fetching all products");
        return repository.findAll();
    }

    public Product getById(String id) {
//        log.info("Fetching product with id: {}", id);
        return repository.findById(id).orElse(null);
    }

    public List<Product> saveAll(List<Product> products) {
        return repository.saveAll(products);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}


