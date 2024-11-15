package com.jv.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jv.demo.model.Genre;

@Service
public interface GenreService {
	
	List<Genre> getGenres();
	
	Genre saveGenre(Genre genre);
	
	Genre updateGenre(Genre genre);
	
	void deleteGenre(Long id);

}
