package com.jv.demo.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "genres")
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor	
public class Genre extends BaseEntity {
	
	@Column(name = "genre_name")
	@NotBlank
	private String genreName;
	
	@ManyToMany(mappedBy = "genresSet")
	Set<Movie> moviesSet;
}
