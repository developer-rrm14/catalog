package com.rrm14.catalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.rrm14.catalog.dto.ProductDTO;
import com.rrm14.catalog.entities.Category;
import com.rrm14.catalog.entities.Product;
import com.rrm14.catalog.repositories.CategoryRepository;
import com.rrm14.catalog.repositories.ProductRepository;
import com.rrm14.catalog.services.exceptions.DataBaseException;
import com.rrm14.catalog.services.exceptions.ResourceNotFoundException;
import com.rrm14.catalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private Product product;
	private PageImpl<Product> page;
	private Category category;
	private ProductDTO productDTO;
	private Long existingCategoryId;
	private Long NoExistingCategoryId;
	
	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 4L;
		product = Factory.createProduct();
		page = new PageImpl<>(List.of(product));
		category = Factory.createCategory();
		productDTO = Factory.createProductDTO();
		existingCategoryId = 2L;
		NoExistingCategoryId = 5L;
		
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
		
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.getOne(existingId)).thenReturn(product);
		
		Mockito.when(categoryRepository.getOne(existingCategoryId)).thenReturn(category);
		
		Mockito.when(repository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(categoryRepository.getOne(NoExistingCategoryId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		
		Mockito.doThrow(EmptyResultDataAccessException.class)
			.when(repository).deleteById(nonExistingId);
		
		Mockito.doThrow(DataIntegrityViolationException.class)
			.when(repository).deleteById(dependentId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {	
		Assertions.assertDoesNotThrow(()-> {
			service.delete(existingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {	
		Assertions.assertThrows(ResourceNotFoundException.class, ()-> {
			service.delete(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIdHasIntegrity() {	
		Assertions.assertThrows(DataBaseException.class, ()-> {
			service.delete(dependentId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	}
	
	@Test
	public void findAllPagedShouldReturnPage() {	
		Pageable pageable = PageRequest.of(0, 10);
		Page<ProductDTO> result = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		
		Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
	}
	
	@Test
	public void findByIdShouldObjectWhenIdExists() {	
		
		ProductDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		
		Mockito.verify(repository, Mockito.times(1)).findById(existingId);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {	
		
		Assertions.assertThrows(ResourceNotFoundException.class, ()-> {
			service.findById(nonExistingId);
		});
		
		Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);
	}
	
	@Test
	public void updateShouldProductObjectWhenIdExists() {	
		
		ProductDTO result = service.update(existingId, productDTO);
		
		Assertions.assertNotNull(result);
		
		Mockito.verify(repository, Mockito.times(1)).getOne(existingId);
		Mockito.verify(categoryRepository, Mockito.times(1)).getOne(existingCategoryId);
		Mockito.verify(repository, Mockito.times(1)).save(product);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, ()-> {
			service.update(nonExistingId, productDTO);
		});
			
		Mockito.verify(repository, Mockito.times(1)).getOne(nonExistingId);
		
	}
}
