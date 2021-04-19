package com.edisonmaciel.dscatalog.dto;

import java.io.Serializable;

import com.edisonmaciel.dscatalog.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	
	public CategoryDTO (Category entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}

}
