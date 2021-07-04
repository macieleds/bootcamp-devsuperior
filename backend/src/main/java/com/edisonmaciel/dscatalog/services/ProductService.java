package com.edisonmaciel.dscatalog.services;

import com.edisonmaciel.dscatalog.dto.CategoryDTO;
import com.edisonmaciel.dscatalog.dto.ProductDTO;
import com.edisonmaciel.dscatalog.entities.Category;
import com.edisonmaciel.dscatalog.entities.Product;
import com.edisonmaciel.dscatalog.repositories.CategoryRepository;
import com.edisonmaciel.dscatalog.repositories.ProductRepository;
import com.edisonmaciel.dscatalog.services.exceptions.DatabaseException;
import com.edisonmaciel.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(Long categoryId, PageRequest pageRequest){
		Category category = (categoryId == 0) ? null : categoryRepository.getOne(categoryId);
		Page<Product> list = productRepository.find(category, pageRequest);
		return list.map(ProductDTO::new);
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		var entity = new Product();
		copyDTOtoEntity(dto, entity);
		entity = productRepository.save(entity);
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			var entity = productRepository.getOne(id);
			copyDTOtoEntity(dto, entity);
			entity = productRepository.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
		
	}
	
	private void copyDTOtoEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());	
		
		entity.getCategories().clear();
		for(CategoryDTO catDto : dto.getCategories()) {
			var category = categoryRepository.getOne(catDto.getId());
			entity.getCategories().add(category);
		}
	}
}
