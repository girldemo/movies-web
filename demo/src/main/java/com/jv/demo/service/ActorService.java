package com.jv.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jv.demo.model.Actor;

@Service
public interface ActorService {
	
	List<Actor> saveActor(List<Actor> actors);
	List<Actor> getActors();
}
