package com.aixtor.ecommerce.product.service.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private String brand;
    private String category;
    private String description;
    private Map<String, Object> attributes;
    private double price;
    private List<Variant> variants;
    private List<String> images;
    private Date createdAt = new Date();

    public Product(String id, String name, String brand, String category, String description, Map<String, Object> attributes, double price, List<String> images, List<Variant> variants, Date createdAt) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.attributes = attributes;
        this.price = price;
        this.images = images;
        this.variants = variants;
        this.createdAt = createdAt;
    }

    public Product() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Product{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", brand='" + brand + '\'' + ", category='" + category + '\'' + ", description='" + description + '\'' + ", attributes=" + attributes + ", price=" + price + ", variants=" + variants + ", images=" + images + ", createdAt=" + createdAt + '}';
    }

}
