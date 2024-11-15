package com.jv.demo.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "movies")
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor	
public class Movie extends BaseEntity{
	
	private String title;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(name = "release_year")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate releaseYear;
	
	private int duration;
	
	@Column(name = "poster_url")
	private String posterUrl;
	
	@Column(name = "trailer_url")
	private String trailerUrl;
	
	private String language;
	
	@Column(name = "imdp_score")
	private float imdpScore;
	
	@ManyToMany
	@JoinTable(
		name = "movies_has_genres",
		joinColumns = @JoinColumn(name = "movie_id"),
		inverseJoinColumns = @JoinColumn(name = "genre_id"))
	Set<Genre> genresSet;
	
	@ManyToMany
	@JoinTable(
		name = "movies_has_directors",
		joinColumns = @JoinColumn(name = "movie_id"),
		inverseJoinColumns = @JoinColumn(name = "director_id"))
	Set<Director> directorsSet;	

	@ManyToMany
	@JoinTable(
		name = "movies_has_actors",
		joinColumns = @JoinColumn(name = "movie_id"),
		inverseJoinColumns = @JoinColumn(name = "actor_id"))
	Set<Actor> actorsSet;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "video_id", insertable = false, updatable = false)
	private Video video;
	 
	@ManyToMany(mappedBy = "moviesSet")
	Set<History> historySet; 
	
    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Comment> commentsSet;
    
    @OneToMany(mappedBy = "movie")
    private Set<Rating> ratings;
}
