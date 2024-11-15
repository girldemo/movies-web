package com.jv.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jv.demo.model.Genre;
import com.jv.demo.service.GenreService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/genres")
public class GenreController {
	
	@Autowired
	private GenreService genreService;
	
	@GetMapping
	public ResponseEntity<List<Genre>> getAllGenres() {
		return new ResponseEntity<List<Genre>>(genreService.getGenres(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Genre> saveGenre(@Valid @RequestBody Genre genre) {
		return new ResponseEntity<Genre>(genreService.saveGenre(genre), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody Genre genre) {
		genre.setId(id);
		return new ResponseEntity<Genre>(genreService.saveGenre(genre), HttpStatus.OK);
	}
	
	@DeleteMapping
	public void deleteGenre(@RequestParam Long id) {
		genreService.deleteGenre(id);
	}
}
