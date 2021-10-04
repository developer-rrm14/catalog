package com.rrm14.catalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rrm14.catalog.constants.Constants;
import com.rrm14.catalog.dto.CategoryDTO;
import com.rrm14.catalog.entities.Category;
import com.rrm14.catalog.repositories.CategoryRepository;
import com.rrm14.catalog.services.exceptions.DataBaseException;
import com.rrm14.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
//	@Transactional(readOnly = true)
//	public List<CategoryDTO> findAll(){
//		List<Category> list = repository.findAll();
//		return list.stream().map(item-> new CategoryDTO(item)).collect(Collectors.toList());			
//	}
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable){
		Page<Category> list = repository.findAll(pageable);
		return list.map(item-> new CategoryDTO(item));			
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(()-> new ResourceNotFoundException(Constants.ENTITY_NOT_FOUND));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(Constants.ID_NOT_FOUND + id);
		}	
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(Constants.ID_NOT_FOUND + id);
		}catch(DataIntegrityViolationException e) {
			throw new DataBaseException(Constants.INTEGRITY_VIOLATION);
		}	
		
	}
}
