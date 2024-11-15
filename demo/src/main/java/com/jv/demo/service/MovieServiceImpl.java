package com.jv.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jv.demo.model.Actor;
import com.jv.demo.model.Director;
import com.jv.demo.model.Genre;
import com.jv.demo.model.Movie;
import com.jv.demo.model.Video;
import com.jv.demo.pojo.PageResult;
import com.jv.demo.repository.MovieRepository;
import com.jv.demo.specification.MovieSpecification;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	private MovieRepository movieRepo;
	
	@Override
	public Movie saveMovie(Movie movie, Set<Genre> genres, Set<Director> directors, Set<Actor> actors) {
		movie.setGenresSet(genres);
		movie.setDirectorsSet(directors);
		movie.setActorsSet(actors);
		return movieRepo.save(movie);
	}

	@Override
	public PageResult<Movie> getMovies(int pageNumber, int pageSize) {
		Pageable pages = PageRequest.of(pageNumber, pageSize, Direction.DESC, "id");
		Page<Movie> pageMovies = movieRepo.findAll(pages);
		
		PageResult<Movie> result = new PageResult<>();
		result.setData(pageMovies.getContent());
		result.setTotalPages(pageMovies.getTotalPages());
		result.setTotalElements(pageMovies.getTotalElements());
		
		return result;
	}

	@Override
	public List<Object> gettMoviesWithGenre() {
		return movieRepo.findMoviesWithGenres(); 
	}

	@Override
	public Object getSingleMovie(Long id) {
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent()) {
			return movieRepo.findMovieById(id);
		}
		throw new RuntimeException("not found id " + id);
	}

	@Override
	public Movie updateMovie(Movie movie, Set<Genre> genres, Set<Director> directors, Set<Actor> actors) {
		movie.setGenresSet(genres);
		movie.setDirectorsSet(directors);
		movie.setActorsSet(actors);
		return movieRepo.save(movie);
	}

	@Override
	public void deleteMovie(Long id) {
		Optional<Movie> movie = movieRepo.findById(id);
		if (movie.isPresent()) {
			movieRepo.deleteById(id);
		}
		else throw new RuntimeException("not found id " + id);
	}

	@Override
	public List<Object> getAllYearsAndNationsByMovies() {
		return movieRepo.findAllYearsAndNationsByMovies();
	}

	@Override
	public PageResult<Movie> getMoviesOrderByUpdateAt(int pageNumber, int pageSize) {
		Pageable pages = PageRequest.of(pageNumber, pageSize);
		Page<Movie> pageMovies = movieRepo.getMoviesOrderByUpdateAt(pages);
		
		PageResult<Movie> result = new PageResult<>();
		result.setData(pageMovies.getContent());
		result.setTotalPages(pageMovies.getTotalPages());
		result.setTotalElements(pageMovies.getTotalElements());
	
		return result;
	}

	@Override
	public PageResult<Movie> getMoviesByGenreId(Long genreId, int pageNumber, int pageSize) {
		Pageable pages = PageRequest.of(pageNumber, pageSize);
		Page<Movie> pageMovies = movieRepo.findByGenresSetId(genreId, pages);
		
		PageResult<Movie> result = new PageResult<>();
		result.setData(pageMovies.getContent());
		result.setTotalPages(pageMovies.getTotalPages());
		result.setTotalElements(pageMovies.getTotalElements());
		
		return result;
	}

	@Override
	public PageResult<Movie> getMoviesByLanguage(String languageName, int pageNumber, int pageSize) {
		Pageable pages = PageRequest.of(pageNumber, pageSize);
		Page<Movie> pageMovies = movieRepo.findByLanguage(languageName, pages);
		
		PageResult<Movie> result = new PageResult<>();
		result.setData(pageMovies.getContent());
		result.setTotalPages(pageMovies.getTotalPages());
		result.setTotalElements(pageMovies.getTotalElements());
		
		return result;
	}

	@Override
	public PageResult<Movie> getMoviesByReleaseYear(Long year, int pageNumber, int PageSize) {
		Pageable pages = PageRequest.of(pageNumber, PageSize);
		Page<Movie> pageMovies = movieRepo.findByReleaseYear(year, pages);
		
		PageResult<Movie> result = new PageResult<>();
		result.setData(pageMovies.getContent());
		result.setTotalPages(pageMovies.getTotalPages());
		result.setTotalElements(pageMovies.getTotalElements());
		
		return result;
	}

	@Override
	public PageResult<Movie> getMoviesOrderByCreateAt(int pageNumber, int pageSize) {
		Pageable pages = PageRequest.of(pageNumber, pageSize);
		Page<Movie> pageMovies = movieRepo.getMoviesOrderByCreateAt(pages);
		
		PageResult<Movie> result = new PageResult<>();
		result.setData(pageMovies.getContent());
		result.setTotalPages(pageMovies.getTotalPages());
		result.setTotalElements(pageMovies.getTotalElements());
	
		return result;
	}

	@Override
	public PageResult<Movie> getMoviesByTrailer(int pageNumber, int pageSize) {
		Pageable pages = PageRequest.of(pageNumber, pageSize);
		Page<Movie> pageMovies = movieRepo.getMoviesByTrailer(pages);
		
		PageResult<Movie> result = new PageResult<>();
		result.setData(pageMovies.getContent());
		result.setTotalPages(pageMovies.getTotalPages());
		result.setTotalElements(pageMovies.getTotalElements());
		
		return result;
	}

	@Override
	public Video getVideoByMovie(Long id) {
		return movieRepo.getVideoByMovie(id);
	}

	@Override
	public PageResult<Movie> getMoviesByTitle(String name, int pageNumber, int pageSize) {
		Pageable pages = PageRequest.of(pageNumber, pageSize);
		Page<Movie> pageMovies = movieRepo.getMoviesByTitle(name, pages);
		
		PageResult<Movie> result = new PageResult<>();
		result.setData(pageMovies.getContent());
		result.setTotalPages(pageMovies.getTotalPages());
		result.setTotalElements(pageMovies.getTotalElements());
		
		return result;
	}

	@Override
	public PageResult<Movie> searchMovies(String genre, String nation, Integer year, String sortField, boolean asc, int pageNumber, int pageSize) {
		Specification<Movie> spec = Specification.where(MovieSpecification.hasGenre(genre))
												 .and(MovieSpecification.hasNation(nation))
												 .and(MovieSpecification.hasYear(year))
												 .and(MovieSpecification.orderBy(sortField, asc));
		
		Pageable pages = PageRequest.of(pageNumber, pageSize);
		Page<Movie> pageMovies = movieRepo.findAll(spec, pages);
		
		PageResult<Movie> result = new PageResult<>();
		result.setData(pageMovies.getContent());
		result.setTotalPages(pageMovies.getTotalPages());
		result.setTotalElements(pageMovies.getTotalElements());
		
		return result;
	}

	@Override
	public List<Movie> getMoviesByIds(List<Long> ids) {
		return movieRepo.findMoviesByIds(ids);
	}
	
}
