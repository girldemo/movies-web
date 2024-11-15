package com.jv.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jv.demo.model.Movie;
import com.jv.demo.model.Video;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie>{
	
	@Query("SELECT m, GROUP_CONCAT(g.genreName) AS genresSet " +
            "FROM Movie m " +
            "LEFT JOIN m.genresSet g " +
            "GROUP BY m")
	List<Object> findMoviesWithGenres();
	
	@Query("SELECT m, GROUP_CONCAT(distinct g.genreName), "
			  + "GROUP_CONCAT(distinct d.directorName), "
			  + "GROUP_CONCAT(distinct a.actorName) "
			  + "FROM Movie m "
		      + "LEFT JOIN m.genresSet g "
		      + "LEFT JOIN m.directorsSet d "
		      + "LEFT JOIN m.actorsSet a "
		      + "WHERE m.id = :id "
		      + "GROUP BY m")
	Object findMovieById(Long id);
	
	@Query("select YEAR(m.releaseYear) as year, m.language from Movie m")
	List<Object> findAllYearsAndNationsByMovies();
	
	@Query("select m from Movie m order by m.updateAt desc")
	Page<Movie> getMoviesOrderByUpdateAt(Pageable pages);
	
	@Query("select m from Movie m order by m.createAt desc")
	Page<Movie> getMoviesOrderByCreateAt(Pageable pages);
	
	Page<Movie> findByGenresSetId(Long genreId, Pageable pages);
	
	Page<Movie> findByLanguage(String languageName, Pageable pages);
	
	@Query("select m from Movie m where YEAR(m.releaseYear) = :year")
	Page<Movie> findByReleaseYear(Long year, Pageable pages);
	
	@Query("select m from Movie m where m.video is null")
	Page<Movie> getMoviesByTrailer(Pageable pages);
	
	@Query("select v from Movie m join Video v on m.video.id = v.id where m.id = :id")
//	@Query("select v from Video v where v.id = :id")
	Video getVideoByMovie(Long id);
	
	@Query("select m from Movie m where m.title like %:name%")
	Page<Movie> getMoviesByTitle(String name, Pageable pages);
	
	@Query("select m from Movie m where m.id in :ids")
	List<Movie> findMoviesByIds(@Param("ids") List<Long> ids);
}
