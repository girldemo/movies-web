package com.jv.demo.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "comments")
@Builder
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseEntity{
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@Column(name = "comment_date")
	private LocalDate commentDate;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
