package com.jv.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jv.demo.model.Actor;
import com.jv.demo.model.Director;
import com.jv.demo.model.Genre;
import com.jv.demo.model.Movie;
import com.jv.demo.model.Video;
import com.jv.demo.pojo.PageResult;

@Service
public interface MovieService {
	
	Movie saveMovie(Movie movie, Set<Genre> genres, Set<Director> directors, Set<Actor> actors);
	
	PageResult<Movie> getMovies(int pageNumber, int pageSize);
	
	List<Object> getAllYearsAndNationsByMovies();
	
	List<Object> gettMoviesWithGenre();
	
	Object getSingleMovie(Long id);
	
	Movie updateMovie(Movie movie, Set<Genre> genres, Set<Director> directors, Set<Actor> actors);
	
	void deleteMovie(Long id);
	
	PageResult<Movie> getMoviesOrderByUpdateAt(int pageNumber, int pageSize);
	
	PageResult<Movie> getMoviesOrderByCreateAt(int pageNumber, int pageSize);
	
	PageResult<Movie> getMoviesByGenreId(Long genreId, int pageNumber, int pageSize);
	
	PageResult<Movie> getMoviesByLanguage(String languageName, int pageNumber, int pageSize);
	
	PageResult<Movie> getMoviesByReleaseYear(Long year, int pageNumber, int pageSize);
	
	PageResult<Movie> getMoviesByTrailer(int pageNumber, int pageSize);
	
	Video getVideoByMovie(Long id);
	
	PageResult<Movie> getMoviesByTitle(String name, int pageNumber, int pageSize);
	
	PageResult<Movie> searchMovies(String genre, String nation, Integer year, String sortField, boolean asc, int PageNumber, int pageSize);
	
	List<Movie> getMoviesByIds(List<Long> ids);
}

