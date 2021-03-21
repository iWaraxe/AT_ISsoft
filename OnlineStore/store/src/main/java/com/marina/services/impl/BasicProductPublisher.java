package com.marina.services.impl;

import com.github.javafaker.Faker;
import com.marina.Category;
import com.marina.Product;
import com.marina.constants.Constants;
import com.marina.dao.builder.QueryBuilder;
import com.marina.dao.commonDAO.ProductsDAO;

import java.util.ArrayList;
import java.util.List;

public class BasicProductPublisher {
    private Faker faker = new Faker();

    protected List<Product> publicProducts(List<Category> categories){
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < Constants.PRODUCT_COUNT_IN_CATEGORY; i++) {
            categories.forEach(category -> {
                String productName = populateProductNameByCategoryName(category.getCategoryName());
                Product product = Product.builder()
                        .name(productName)
                        .categoryID(category.getCategoryID())
                        .price(Double.valueOf(faker.commerce().price()))
                        .quantity(faker.number().numberBetween(0, 30))
                        .rating(faker.number().numberBetween(0, 11))
                        .build();
                products.add(product);
            });
        }
        new ProductsDAO().insertProducts(products);
        return getAllProducts();
    }

    private String populateProductNameByCategoryName(String categoryName) {
        categoryName = categoryName.trim().toLowerCase();
        String productName = "";
        if (categoryName.equals("fruit")) {
            productName = faker.food().fruit();
        } else if (categoryName.equals("spice")) {
            productName = faker.food().spice();
        } else if (categoryName.equals("vegetable")) {
            productName = faker.food().vegetable();
        }
        return productName;
    }

    private List<Product> getAllProducts(){
        return new ProductsDAO().loadProducts(QueryBuilder.getAllProducts());
    }
}