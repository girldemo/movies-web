package com.jv.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jv.demo.model.Genre;
import com.jv.demo.repository.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService{

	@Autowired
	private GenreRepository genreRepo;

	@Override
	public Genre saveGenre(Genre genre) {
		return genreRepo.save(genre);
	}

	@Override
	public Genre updateGenre(Genre genre) {
		return genreRepo.save(genre);
	}

	@Override
	public List<Genre> getGenres() {
		return genreRepo.findAll();
	}

	@Override
	public void deleteGenre(Long id) {
		genreRepo.deleteById(id);
	}
}
