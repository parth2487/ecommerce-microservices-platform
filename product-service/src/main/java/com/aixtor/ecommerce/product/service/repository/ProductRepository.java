package com.aixtor.ecommerce.product.service.repository;

import com.aixtor.ecommerce.product.service.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<com.aixtor.ecommerce.product.service.model.Product, String> {
}
