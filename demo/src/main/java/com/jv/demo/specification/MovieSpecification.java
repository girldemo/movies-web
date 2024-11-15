package com.jv.demo.specification;

import org.springframework.data.jpa.domain.Specification;

import com.jv.demo.model.Genre;
import com.jv.demo.model.Movie;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class MovieSpecification {
	public static Specification<Movie> hasGenre(String genre) {
		return (root, query, criteriaBuilder) -> {
			if (genre == null) {
				return null;
			}
			Join<Movie, Genre> join = root.join("genresSet", JoinType.INNER);
			
			return criteriaBuilder.equal(join.get("genreName"), genre);
		};
	}
	
	public static Specification<Movie> hasNation(String nation) {
		return (root, query, criteriaBuilder) -> 
			nation == null ? null : criteriaBuilder.equal(root.get("language"), nation);
	}
	
	public static Specification<Movie> hasYear(Integer year) {
		return (root, query, criteriaBuilder) -> {
            if (year == null) {
                return null;
            }
            CriteriaBuilder.In<Integer> inClause = criteriaBuilder.in(criteriaBuilder.function("YEAR", Integer.class, root.get("releaseYear")));
            inClause.value(year);
            
            return inClause;
        };
	}
	
	public static Specification<Movie> orderBy(String field, boolean asc) {
		return (root, query, criteriaBuilder) -> {
            query.orderBy(asc ? criteriaBuilder.asc(root.get(field)) : criteriaBuilder.desc(root.get(field)));
            return null;
        };
	}
}
