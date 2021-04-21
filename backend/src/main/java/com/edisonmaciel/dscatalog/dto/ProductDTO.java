package com.edisonmaciel.dscatalog.dto;

import com.edisonmaciel.dscatalog.entities.Category;
import com.edisonmaciel.dscatalog.entities.Product;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Size(min = 2, max = 60, message = "Deve ter entre 2 e 60 caracteres")
	@NotBlank(message = "Campo obrigatório")
	private String name;

	@NotBlank
	private String description;

	@Positive(message = "O preço deve ser um valor positivo")
	private Double price;
	private String imgUrl;

	@PastOrPresent(message = "A data do produto não pode ser futura")
	private Instant date;
	
	private List<CategoryDTO> categories = new ArrayList<>();

	public ProductDTO(Product entity) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
		this.date = entity.getDate();
	}
	
	public ProductDTO(Product entity, Set<Category> categories) {
		this(entity);
		categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));
	}
	
	
		
}
