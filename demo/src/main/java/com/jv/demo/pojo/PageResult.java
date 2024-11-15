package com.jv.demo.pojo;

import java.util.List;

import lombok.Data;

@Data
public class PageResult<T> {
	
	private List<T> data;
	private int totalPages;
	private long totalElements;
}
