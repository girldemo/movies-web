package com.jv.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.jv.demo.model.Movie;
import com.jv.demo.model.Video;
import com.jv.demo.pojo.PageResult;
import com.jv.demo.service.MovieService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@GetMapping
	ResponseEntity<PageResult<Movie>> getAllMovies() {
		return new ResponseEntity<PageResult<Movie>>((movieService.getMovies(0, 3)), HttpStatus.OK);
	}
	
	@PostMapping
	ResponseEntity<Movie> saveMovie(@Valid @RequestBody Movie movie) {
		return new ResponseEntity<Movie>(movieService.saveMovie(movie, movie.getGenresSet(), 
																	   movie.getDirectorsSet(),
																	   movie.getActorsSet()), HttpStatus.OK);
	}
	
	@GetMapping("/genres")
	ResponseEntity<List<Object>> getAllMoviesWithGenres() {
		return new ResponseEntity<List<Object>>(movieService.gettMoviesWithGenre(), HttpStatus.OK);
	}
	
	@GetMapping("/datesAndNations")
	ResponseEntity<List<Object>> getAllYearsAndNationsByMovies() {
		return new ResponseEntity<List<Object>>(movieService.getAllYearsAndNationsByMovies(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<Object> getSingleMovieById(@PathVariable Long id) {
		return new ResponseEntity<Object>(movieService.getSingleMovie(id), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Movie> updateGenre(@PathVariable Long id, @RequestBody Movie movie) {
		movie.setId(id);
		return new ResponseEntity<Movie>(movieService.updateMovie(movie, movie.getGenresSet(), movie.getDirectorsSet(), movie.getActorsSet()), HttpStatus.OK);
	}
	
	@DeleteMapping
	public void deleteMovieById(@RequestParam Long id) {
		movieService.deleteMovie(id);
	}
	
	@GetMapping("/updated")
	public ResponseEntity<PageResult<Movie>> getMoviesOrderByUpdateAt(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		return new ResponseEntity<PageResult<Movie>>(movieService.getMoviesOrderByUpdateAt(pageNumber, pageSize), HttpStatus.OK);
	}
	
	@GetMapping("/genres/{id}")
	public ResponseEntity<PageResult<Movie>> getMoviesByGenresId(@PathVariable Long id, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		return new ResponseEntity<PageResult<Movie>>(movieService.getMoviesByGenreId(id, pageNumber, pageSize), HttpStatus.OK);
	}
	
	@GetMapping("/nations/{name}")
	public ResponseEntity<PageResult<Movie>> getMoviesByNationsName(@PathVariable String name, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		return new ResponseEntity<PageResult<Movie>>(movieService.getMoviesByLanguage(name, pageNumber, pageSize), HttpStatus.OK);
	}
	
	@GetMapping("/years/{year}")
	public ResponseEntity<PageResult<Movie>> getMoviesByReleaseYear(@PathVariable Long year, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		return new ResponseEntity<PageResult<Movie>>(movieService.getMoviesByReleaseYear(year, pageNumber, pageSize), HttpStatus.OK);
	}
	
	@GetMapping("/created")
	public ResponseEntity<PageResult<Movie>> getMoviesOrderByCreateAt(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		return new ResponseEntity<PageResult<Movie>>(movieService.getMoviesOrderByCreateAt(pageNumber, pageSize), HttpStatus.OK);
	}
	
	@GetMapping("/trailer")
	public ResponseEntity<PageResult<Movie>> getMoviesByTrailer(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		return new ResponseEntity<PageResult<Movie>>(movieService.getMoviesByTrailer(pageNumber, pageSize), HttpStatus.OK);
	}
	
	@GetMapping("/videos/{id}")
	public ResponseEntity<Video> getMovieByVideo(@PathVariable Long id) {
		return new ResponseEntity<Video>(movieService.getVideoByMovie(id), HttpStatus.OK);
	}
	
	@GetMapping("/title/{name}")
	public ResponseEntity<PageResult<Movie>> getMoviesByTitle(@PathVariable String name, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		return new ResponseEntity<PageResult<Movie>>(movieService.getMoviesByTitle(name, pageNumber, pageSize), HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<PageResult<Movie>> searchMovies(
			@RequestParam(required = false) String genre,
			@RequestParam(required = false) String nation,
			@RequestParam(required = false) Integer releaseYear,
			@RequestParam(required = false, defaultValue = "title") String sortField,
            @RequestParam(required = false, defaultValue = "true") boolean asc,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize
			) {
		PageResult<Movie> movies = movieService.searchMovies(genre, nation, releaseYear, sortField, asc, pageNumber, pageSize);
		return ResponseEntity.ok(movies);
	}
	
	@PostMapping("/by-ids")
	public List<Movie> getMoviesByIds(@RequestBody List<Long> ids) {
		return movieService.getMoviesByIds(ids);
	}
}
