package com.jv.demo.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "history")
@Builder
@EqualsAndHashCode(callSuper = true)
public class History extends BaseEntity{
	
	@Column(name = "watch_date")
	private LocalDate watchDate;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", insertable = false, updatable = false)
	private Account account;
	
	@ManyToMany
	@JoinTable(
		name = "history_has_movies",
		joinColumns = @JoinColumn(name = "history_id"),
		inverseJoinColumns = @JoinColumn(name = "movie_id"))
	Set<Movie> moviesSet;
}
