package com.rrm14.catalog.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rrm14.catalog.entities.Category;
import com.rrm14.catalog.entities.Product;
import com.rrm14.catalog.constants.Queries;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query(Queries.JPQL_PRODUCT)
	Page<Product> find(Pageable pageable, List<Category> categories, String name);
	
	@Query(Queries.JPQL_PRODUCTS_WITH_CATEGORIES)
	List<Product> findProductsWithCategories(List<Product> products);
}
