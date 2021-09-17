package com.rrm14.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rrm14.catalog.constants.Constants;
import com.rrm14.catalog.dto.CategoryDTO;
import com.rrm14.catalog.entities.Category;
import com.rrm14.catalog.repositories.CategoryRepository;
import com.rrm14.catalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = repository.findAll();
		return list.stream().map(item-> new CategoryDTO(item)).collect(Collectors.toList());
				
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(()-> new EntityNotFoundException(Constants.ENTITY_NOT_FOUND));
		return new CategoryDTO(entity);
	}
}
