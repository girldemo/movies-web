package com.jv.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jv.demo.model.Actor;
import com.jv.demo.repository.ActorRepository;

@Service
public class ActorServiceImpl implements ActorService{
	
	@Autowired
	private ActorRepository actorRepo;

	@Override
	public List<Actor> saveActor(List<Actor> actors) {
		return actorRepo.saveAll(actors);
	}

	@Override
	public List<Actor> getActors() {
		return actorRepo.findAll();
	}
}
