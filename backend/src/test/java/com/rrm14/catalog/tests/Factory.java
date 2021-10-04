package com.rrm14.catalog.tests;

import java.time.Instant;

import com.rrm14.catalog.dto.ProductDTO;
import com.rrm14.catalog.entities.Category;
import com.rrm14.catalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.jpg", Instant.parse("2020-07-14T10:00:00Z"));
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static Category createCategory() {
		Category category = new Category(2L, "Eletronics");
		return category;
	}
}
